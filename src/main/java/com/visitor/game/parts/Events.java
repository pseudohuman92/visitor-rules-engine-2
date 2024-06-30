package com.visitor.game.parts;

import com.visitor.game.Event;
import com.visitor.helpers.Arraylist;

import java.util.UUID;

import static com.visitor.game.Event.turnEnd;
import static com.visitor.game.Event.turnStart;
import static java.lang.System.out;

public class Events extends Messaging {
    /**
     * Event Related Methods
     * These are the methods that implements event mechanism.
     * Events are used to implement triggered abilities.
     */
    public void addEvent(Event e, boolean process) {
        eventQueue.add(e);
        if (process)
            processEvents();
    }

    public void addEvent(Event e) {
        addEvent(e, false);
    }

    public void processEvents() {
        if (!eventQueue.isEmpty()) {
            Arraylist<Event> tempQueue = eventQueue;
            eventQueue = new Arraylist<>();
            while (!tempQueue.isEmpty()) {
                Event e = tempQueue.remove(0);
                out.println("Processing Event: " + e.type);
                triggeringCards.get(turnPlayer).forEachInOrder(c -> c.checkEvent(e));
                triggeringCards.get(this.getOpponentId(turnPlayer)).forEachInOrder(c -> c.checkEvent(e));
            }
        }
    }

    void processBeginEvents() {
        //out.println("Starting Begin Triggers");
        addEvent(turnStart(turnPlayer), true);
        //out.println("Ending Begin Triggers");
    }

    void processEndEvents() {
        //out.println("Starting End Triggers");
        addEvent(turnEnd(turnPlayer), true);
        //out.println("Ending End Triggers");
    }

    public void addTriggeringCard(UUID playerId, com.visitor.game.Card t) {
        triggeringCards.get(playerId).add(t);
    }

    public void removeTriggeringCard(com.visitor.game.Card card) {
        triggeringCards.values().forEach(l -> l.remove(card));
    }
}
