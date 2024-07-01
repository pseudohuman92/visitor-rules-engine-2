package com.visitor.game.parts;

import com.visitor.card.properties.Targetable;
import com.visitor.game.Card;
import com.visitor.game.Player;
import com.visitor.helpers.Arraylist;
import com.visitor.protocol.Types;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.visitor.protocol.Types.SelectFromType.NOTYPE;

public class Getters extends Base {
    /**
     * Card Accessor Methods
     * Getting Card objects from various places
     */
    Arraylist<? extends Targetable> getZone(UUID playerId, Game.Zone zone) {
        Arraylist<Targetable> a = new Arraylist<>();
        switch (zone) {
            case Deck:
                return getPlayer(playerId).deck;
            case Hand:
                return getPlayer(playerId).hand;
            case Play:
                return getPlayer(playerId).playArea;
            case Play_With_Player:
                a.add(getPlayer(playerId));
                a.addAll(getPlayer(playerId).playArea);
                return a;
            case Discard_Pile:
                return getPlayer(playerId).discardPile;
            case Stack:
                return stack;
            case Both_Play:
                players.values().forEach(player -> a.addAll(player.playArea));
                return a;
            case Both_Play_With_Players:
                players.values().forEach(player -> a.addAll(player.playArea));
                a.addAll(players.values());
                return a;
            case Players:
                a.addAll(players.values());
                return a;
            default:
                return a;
        }

    }

    // Returns duplicate
    Arraylist<Card> getBothZones(Game.Zone zone) {
        Arraylist<Card> total = new Arraylist<>();
        switch (zone) {
            case Deck:
                players.values().forEach(player -> total.addAll(player.deck));
            case Hand:
                players.values().forEach(player -> total.addAll(player.hand));
            case Play:
                players.values().forEach(player -> total.addAll(player.playArea));
            case Discard_Pile:
                players.values().forEach(player -> total.addAll(player.discardPile));
            case Both_Play:
                players.values().forEach(player -> total.addAll(player.playArea));
            case Stack:
                total.addAll(stack);
        }
        return total;
    }

    Types.SelectFromType getZoneLabel(Game.Zone zone) {
        switch (zone) {
            case Hand:
                return Types.SelectFromType.HAND;
            case Play:
            case Both_Play:
            case Players:
            case Both_Play_With_Players:
            case Play_With_Player:
                return Types.SelectFromType.PLAY;
            case Discard_Pile:
                return Types.SelectFromType.DISCARD_PILE;
            case Stack:
                return Types.SelectFromType.STACK;
            case Deck:
                return Types.SelectFromType.LIST;
            default:
                return NOTYPE;
        }
    }

    Player getPlayer(UUID playerId) {
        return players.get(playerId);
    }

    public UUID getPlayerId(String username) {
        for (Player p : players.values()) {
            if (p.username.equals(username)) return p.getId();
        }
        return null;
    }

    public UUID getOpponentId(UUID playerId) {
        for (UUID id : players.keySet()) {
            if (!id.equals(playerId)) {
                return id;
            }
        }
        return null;
    }

    public Card getCard(UUID targetID) {
        for (Player player : players.values()) {
            Card c = player.getCard(targetID);
            if (c != null) {
                return c;
            }
        }
        for (Card c : stack) {
            if (c.getId().equals(targetID)) {
                return c;
            }
        }
        return null;
    }

    private Card getCardFromZone(UUID playerId, Game.Zone zone, UUID cardId) {
        Arraylist<Card> card = new Arraylist<>();
        getZone(playerId, zone).forEach(c -> {
            if (c.getId().equals(cardId))
                card.add((Card)c);
        });
        assert (card.size() < 2);
        return card.getOrDefault(0, null);
    }

    public Arraylist<Targetable> getAllFrom(UUID playerId, Zone zone, Predicate<Targetable> pred) {
        Arraylist<Targetable> cards = new Arraylist<>();
        getZone(playerId, zone).forEach(c -> {
            if (pred.test(c)) {
                cards.add(c);
            }
        });
        return cards;
    }

    public Arraylist<Targetable> getAllFrom(Game.Zone zone, Predicate<Targetable> pred) {
        return getAllFrom(turnPlayer, zone, pred).putAllIn(getAllFrom(getOpponentId(turnPlayer), zone, pred));
    }

    Arraylist<Card> getAllZones(UUID playerId) {
            Arraylist<Card> cards = new Arraylist<>();
            return cards.putAllIn(
                getPlayer(playerId).deck).putAllIn(
                getPlayer(playerId).hand).putAllIn(
                getPlayer(playerId).playArea).putAllIn(
                getPlayer(playerId).discardPile);
    }

    public Arraylist<Card> getAllCards(Predicate<Targetable> pred) {
        Arraylist<Card> cards = new Arraylist<>();
        getAllZones(turnPlayer).forEach(c -> {
            if (pred.test(c)) {
                cards.add(c);
            }
        });
        getAllZones(getOpponentId(turnPlayer)).forEach(c -> {
            if (pred.test(c)) {
                cards.add(c);
            }
        });
        return cards;
    }

    public Arraylist<UUID> getAllIds(Predicate<Targetable> pred) {
        Arraylist<UUID> a = new Arraylist<>(getAllCards(pred).transform(Targetable::getId));
        a.addAll(getAllPlayerIds(pred));
        return a;
    }

    protected Collection<Player> getAllPlayers(Predicate<Targetable> pred) {
        Arraylist<Player> ids = new Arraylist<>();
        players.values().forEach(i -> {
           if (pred.test(i)){
               ids.add(i);
           }
        });
        return ids;
    }

    protected Collection<UUID> getAllPlayerIds(Predicate<Targetable> pred) {
        Arraylist<UUID> ids = new Arraylist<>();
        players.values().forEach(i -> {
            if (pred.test(i)){
                ids.add(i.getId());
            }
        });
        return ids;
    }

    public Collection<UUID> getAllPlayerIds() {
        Arraylist<UUID> ids = new Arraylist<>();
        players.values().forEach(i -> {
                ids.add(i.getId());
        });
        return ids;
    }


    Arraylist<Card> getAllCardsById(List<UUID> list) {
        return new Arraylist<>(list.stream().map(this::getCard).collect(Collectors.toList()));
    }

    public UUID getId() {
        return id;
    }

    public Arraylist<com.visitor.game.Card> getTopCardsFromDeck(UUID playerId, int i) {
        return getPlayer(playerId).getFromTopOfDeck(i);
    }
}
