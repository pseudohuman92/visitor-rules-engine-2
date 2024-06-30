package com.visitor.game;

import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.HelperFunctions;
import com.visitor.helpers.UUIDHelper;
import com.visitor.protocol.ServerGameMessages;
import com.visitor.protocol.Types;
import com.visitor.server.DraftEndpoint;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import static java.util.UUID.randomUUID;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

public class Draft {
    public static final List<String> cardClasses = HelperFunctions.getClassesInPackage("com.visitor.sets.base");
    public transient Hashmap<UUID, DraftEndpoint> connections;
    public transient Hashmap<UUID, ArrayBlockingQueue<Object>> responses;
    Hashmap<UUID, Player> players;
    Hashmap<UUID, ServerGameMessages.ServerGameMessage> lastMessages;
    Hashmap<UUID, Arraylist<Card>> possiblePicks;
    UUID id;
    int picksReceived;
    int roundPickCount;
    int roundsCompleted;
    boolean started;

    private final Game game = new Game(); //Dummy game

    private final int TOTAL_ROUNDS = 3;

    public Draft(String playerName1, String playerName2) {
        id = randomUUID();
        players = new Hashmap<>();
        connections = new Hashmap<>();
        lastMessages = new Hashmap<>();
        possiblePicks = new Hashmap<>();
        responses = new Hashmap<>();
        started = false;
        picksReceived = 0;
        roundPickCount = 0;
        roundsCompleted = 0;


        Player player1 = new Player(null, playerName1, new String[0]);
        Player player2 = new Player(null, playerName2, new String[0]);
        players.put(player1.getId(), player1);
        players.put(player2.getId(), player2);
        responses.put(player1.getId(), new ArrayBlockingQueue<>(1));
        responses.put(player2.getId(), new ArrayBlockingQueue<>(1));
    }

    public void startDraft() {
        started = true;
        startDraftRound();
    }

    private void startDraftRound() {
        System.out.println("Starting draft round " + (roundsCompleted + 1));
        possiblePicks.clear();
        players.keySet().forEach(playerId ->
                possiblePicks.put(playerId, getRandomCards(playerId, 3))
        );
        System.out.println("Sending first picks");
        possiblePicks.forEach(this::pick);
    }

    private Arraylist<Card> getRandomCards(UUID playerId, int count) {
        Arraylist<Card> cards = new Arraylist<>();
        Arraylist<Integer> indexes = new Arraylist<>();
        System.out.println("Class Count " + cardClasses.size());
        while (indexes.size() < count) {
            int rand;
            do {
                rand = (int) (cardClasses.size() * Math.random());
            } while (indexes.contains(rand));
            indexes.add(rand);
        }

        System.out.println("INDEXES");
        System.out.println(indexes);

        for (int i : indexes) {
            System.out.println("Card Name: " + cardClasses.get(i));
            cards.add(HelperFunctions.createCard(game, playerId, "base." + cardClasses.get(i)));
        }

        System.out.println("CARDS");
        System.out.println(cards);
        return cards;
    }

    private void addLastCards() {
        UUID arbitraryPlayer = possiblePicks.getArbitraryKey();
        players.get(getOpponentId(arbitraryPlayer)).deck.addAll(possiblePicks.get(arbitraryPlayer));
        players.get(arbitraryPlayer).deck.addAll(possiblePicks.get(getOpponentId(arbitraryPlayer)));
    }

    private void swapPossiblePicks() {
        UUID arbitraryPlayer = possiblePicks.getArbitraryKey();
        Arraylist<Card> tempCards = possiblePicks.get(arbitraryPlayer);
        Hashmap<UUID, Arraylist<Card>> tempPossiblePicks = new Hashmap<>();

        tempPossiblePicks.put(arbitraryPlayer, possiblePicks.get(getOpponentId(arbitraryPlayer)));
        tempPossiblePicks.put(getOpponentId(arbitraryPlayer), tempCards);
        possiblePicks = tempPossiblePicks;
    }

    public void processPicks() {
        responses.forEach((playerId, response) -> {
            UUID pickedCardId = null;
            try {
                pickedCardId = (UUID) response.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //System.out.println("Received Pick\nFrom: " + playerId + "\nCard ID: " + pickedCardId);

            Card pickedCard = UUIDHelper.getInList(possiblePicks.get(playerId), new Arraylist<>(pickedCardId)).get(0);
            possiblePicks.get(playerId).remove(pickedCard);
            players.get(playerId).deck.putIn(pickedCard);
        });

        roundPickCount++;
        continueDraft();
    }

    private void continueDraft() {
        if (roundPickCount == 1) {
            System.out.println("Swap possible picks");
            swapPossiblePicks();

            System.out.println("Sending second picks");
            possiblePicks.forEach(this::pick);
        } else {
            roundPickCount = 0;
            //System.out.println("Adding last cards");
            //addLastCards();
            System.out.println("Ending draft round " + (roundsCompleted + 1));

            roundsCompleted++;
            if (roundsCompleted < TOTAL_ROUNDS) {
                startDraftRound();
            } else {
                players.keySet().forEach(this::sendCompleted);
            }
        }
    }

    private void pick(UUID playerId, Arraylist<Card> cards) {
        ServerGameMessages.PickCard.Builder builder = ServerGameMessages.PickCard.newBuilder()
                .addAllCandidates(cards.transform(c -> c.toCardMessage().build()))
                .setMessageP("Pick a card to draft.").setDraft(toDraftState(playerId, false));
        send(playerId, ServerGameMessages.ServerGameMessage.newBuilder().setPickCard(builder));
    }

    private void sendCompleted(UUID playerId) {
        ServerGameMessages.PickCard.Builder builder = ServerGameMessages.PickCard.newBuilder()
                .setMessageP("Draft Completed.").setDraft(toDraftState(playerId, true));
        send(playerId, ServerGameMessages.ServerGameMessage.newBuilder().setPickCard(builder));
    }

    private void send(UUID playerId, ServerGameMessages.ServerGameMessage.Builder builder) {
        try {
            setLastMessage(playerId, builder.build());
            DraftEndpoint e = connections.get(playerId);
            if (e != null) {
                e.send(builder);
            } else {
                System.out.println(playerId + " Connection not found!");
            }
        } catch (IOException | EncodeException ex) {
            getLogger(Game.class.getName()).log(SEVERE, null, ex);
        }
    }

    /**
     * Connection Methods
     * To deal with client connections
     */
    public void addConnection(UUID playerId, DraftEndpoint connection) {
        connections.putIn(playerId, connection);
        if (!started && connections.keySet().size() > 1) {
            startDraft();
        }
    }

    public void removeConnection(UUID playerId) {
        connections.removeFrom(playerId);
    }

    public void setLastMessage(UUID playerId, ServerGameMessages.ServerGameMessage lastMessage) {
        lastMessages.put(playerId, lastMessage);
    }

    public ServerGameMessages.ServerGameMessage getLastMessage(UUID playerId) {
        return lastMessages.get(playerId);
    }

    public UUID getId() {
        return id;
    }

    public UUID getOpponentId(UUID playerId) {
        for (UUID id : players.keySet()) {
            if (!id.equals(playerId)) {
                return id;
            }
        }
        return null;
    }

    public Types.DraftState.Builder toDraftState(UUID playerId, boolean completed) {
        Types.DraftState.Builder b =
                Types.DraftState.newBuilder()
                        .setId(id.toString())
                        .setPlayerId(playerId.toString())
                        .addAllDecklist(players.get(playerId).deck.toDeckList())
                        .setCompleted(completed);
        return b;
    }

    public Types.DraftState.Builder toDraftState(String playerName, boolean completed) {
        return toDraftState(getPlayerId(playerName), completed);
    }

    private UUID getPlayerId(String playerName) {
        for (Player p : players.values()) {
            if (p.username.equals(playerName)) {
                return p.getId();
            }
        }
        return null;
    }

    public void draftPick(UUID playerId, UUID cardId) {
        try {
            responses.get(playerId).put(cardId);
            picksReceived++;
            //TODO: Update the player so their picks gets shown
            if (picksReceived == 2) {
                picksReceived = 0;
                processPicks();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
