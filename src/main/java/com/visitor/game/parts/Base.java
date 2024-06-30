package com.visitor.game.parts;

import com.visitor.game.Card;
import com.visitor.game.Event;
import com.visitor.game.Player;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.protocol.ServerGameMessages.ServerGameMessage;
import com.visitor.protocol.Types.Phase;
import com.visitor.server.GameEndpointInterface;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import static java.util.UUID.randomUUID;

public class Base {
    Arraylist<String> history;

    public transient Hashmap<UUID, GameEndpointInterface> connections;
    public UUID activePlayer;
    public transient ArrayBlockingQueue<Object> response;
    Hashmap<UUID, Player> players;
    Hashmap<UUID, ServerGameMessage> lastMessages;
    UUID turnPlayer;
    Arraylist<Card> stack;
    Phase phase;
    int turnCount;
    int passCount;
    UUID id;
    Hashmap<UUID, Arraylist<Card>> triggeringCards;
    Arraylist<Event> eventQueue;

    Arraylist<UUID> attackers;
    Arraylist<UUID> blockers;

    public Base() {
        history = new Arraylist<>();
        id = randomUUID();
        players = new Hashmap<>();
        connections = new Hashmap<UUID, GameEndpointInterface>();
        stack = new Arraylist<>();
        lastMessages = new Hashmap<>();
        response = new ArrayBlockingQueue<>(1);
        triggeringCards = new Hashmap<>();

        eventQueue = new Arraylist<>();
        attackers = new Arraylist<>();
        blockers = new Arraylist<>();
    }

    public void addToHistory(Object message) {
        history.add(message.toString());
    }

    public enum Zone {
        Deck, Hand, Play, Both_Play, Discard_Pile, None, Players, Play_With_Player, Both_Play_With_Players, Stack
    }
}
