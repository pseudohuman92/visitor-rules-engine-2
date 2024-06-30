/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.game;

import com.visitor.helpers.Arraylist;

import java.util.UUID;

import static com.visitor.game.Event.EventType.*;

/**
 * @author pseudo
 */
public class Event {

    public EventType type;
    public Arraylist<Object> data;

    private Event(EventType l) {
        type = l;
        data = new Arraylist<>();
    }

    //Creators
    public static Event draw(UUID playerId, Arraylist<Card> drawnCards) {
        Event e = new Event(Draw);
        e.data.add(playerId);
        e.data.add(drawnCards);
        return e;
    }

    public static Event discard(UUID playerId, Arraylist<Card> discardedCards) {
        Event e = new Event(Discard);
        e.data.add(playerId);
        e.data.add(discardedCards);
        return e;
    }

    public static Event discard(UUID playerId, Card... discardedCard) {
        Event e = new Event(Discard);
        e.data.add(playerId);
        e.data.add(new Arraylist<>(discardedCard));
        return e;
    }

    public static Event attack(Arraylist<Card> attackers) {
        Event e = new Event(Attack);
        e.data.add(attackers);
        return e;
    }

    public static Event turnStart(UUID turnPlayer) {
        Event e = new Event(Turn_Start);
        e.data.add(turnPlayer);
        return e;
    }

    public static Event turnEnd(UUID turnPlayer) {
        Event e = new Event(Turn_End);
        e.data.add(turnPlayer);
        return e;
    }

    public static Event study(UUID studyingPlayer, Card studiedCard) {
        Event e = new Event(Study);
        e.data.add(studyingPlayer);
        e.data.add(studiedCard);
        return e;
    }

    public static Event destroy(Card destroyingCard, Card destroyedCard) {
        Event e = new Event(Destroy);
        Arraylist<Card> cards = new Arraylist<>();
        cards.add(destroyingCard);
        cards.add(destroyedCard);
        e.data.add(cards);
        return e;
    }

    public static Event sacrifice(Card sacrificedCard) {
        Event e = new Event(Sacrifice);
        e.data.add(sacrificedCard);
        return e;
    }

    public static Event death(Card diedCard) {
        Event e = new Event(Death);
        e.data.add(diedCard);
        return e;
    }

    public static Event transform(Card transformingCard, Card transformedFrom, Card transformedTo) {
        Event e = new Event(Transform);
        Arraylist<Card> cards = new Arraylist<>();
        cards.add(transformingCard);
        cards.add(transformedFrom);
        cards.add(transformedTo);
        e.data.add(cards);
        return e;
    }

    public static Event playCard(Card card) {
        Event e = new Event(Play_Card);
        e.data.add(card);
        return e;
    }

    public static Event enterPlay(Card card) {
        Event e = new Event(Enter_Play);
        e.data.add(card);
        return e;
    }

    public static Event damage(Card card, UUID targetId, com.visitor.helpers.containers.Damage damage) {
        Event e = new Event(Damage);
        e.data.add(card);
        e.data.add(targetId);
        e.data.add(damage);
        return e;
    }

    //Checkers
    public boolean playersTurnStart(UUID playerId) {
        return (type == Turn_Start && data.get(0).equals(playerId));
    }

    public boolean playersTurnEnd(UUID playerId) {
        return (type == Turn_End && data.get(0).equals(playerId));
    }

    public enum EventType {
        Turn_Start, Turn_End,
        Draw, Discard, Study,
        Destroy, Transform,
        Play_Card, Sacrifice, Enter_Play, Damage, Death, Attack
    }

}
