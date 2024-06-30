/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.card.properties;

import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.Card;
import com.visitor.game.Event;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;

/**
 * Interface for cards that has a triggering effect.
 *
 * @author pseudo
 */
public class Triggering {

    private final Card card;
    private final Game game;

    private final Arraylist<EventChecker> eventCheckerList;


    public Triggering(Game game, Card card) {
        this.card = card;
        this.game = game;
        eventCheckerList = new Arraylist<>();
    }

    public Triggering(Game game, Card card, EventChecker eventChecker) {
        this(game, card);
        eventCheckerList.add(eventChecker);
    }

    public final void checkEvent(Event event) {
        eventCheckerList.forEachInOrder(ec -> ec.accept(event));
    }

    public final Triggering register() {
        game.addTriggeringCard(card.controller, card);
        return this;
    }

    public final Triggering deregister() {
        game.removeTriggeringCard(card);
        return this;
    }

    //Adders
    public final Triggering addEventChecker(EventChecker eventChecker) {
        eventCheckerList.add(eventChecker);
        return this;
    }

    // Resetters
    public final void resetEventCheckerList() {
        eventCheckerList.clear();
    }
}
