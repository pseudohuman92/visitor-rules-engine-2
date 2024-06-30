/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.helpers;

import com.visitor.card.properties.Targetable;
import com.visitor.game.Card;
import com.visitor.game.Player;
import com.visitor.protocol.Types;

import java.util.UUID;
import java.util.function.Predicate;

import static com.visitor.game.Card.CardSubtype.Cantrip;
import static com.visitor.game.Card.CardSubtype.Ritual;
import static com.visitor.game.Card.CardType.*;
import static com.visitor.protocol.Types.Knowledge.*;

/**
 * @author pseudo
 */
public abstract class Predicates {

    public static boolean isAsset(Card card) {
        return card.hasType(Asset);
    }

    public static boolean isSpell(Card card) {
        return card.hasType(Spell);
    }

    public static boolean isCantrip(Card card) {
        return card.hasSubtype(Cantrip);
    }

    public static boolean isRitual(Card card) {
        return card.hasSubtype(Ritual);
    }

    public static boolean isAlly(Card card) {
        return card.hasType(Ally);
    }

    public static boolean isJunk(Card card) {
        return card.hasType(Junk);
    }

    public static boolean isUnit(Targetable card) {
        return isCard(card) && ((Card)card).hasType(Unit);
    }

    public static boolean isStudiable(Card c) {
        return c.isStudiable();
    }

    public static boolean isGreen(Card c) {
        return c.hasColor(GREEN);
    }

    public static boolean any(Object o) {
        return true;
    }

    public static boolean none(Object o) {
        return false;
    }


    @SafeVarargs
    public static <T> Predicate<T> and(Predicate<T>... predicates) {
        return (t -> {
            for (Predicate<T> predicate : predicates) {
                if (!predicate.test(t))
                    return false;
            }
            return true;
        });
    }

    @SafeVarargs
    public static <T> Predicate<T> or(Predicate<T>... predicates) {
        return (t -> {
            for (Predicate<T> predicate : predicates) {
                if (predicate.test(t))
                    return true;
            }
            return false;
        });
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return (t -> !predicate.test(t));
    }

    public static boolean isPurple(Card card) {
        return card.hasColor(PURPLE);
    }

    public static boolean isRed(Card card) {
        return card.hasColor(RED);
    }

    public static boolean isDepleted(Card card) {
        return card.isDepleted();
    }

    public static Predicate<Card> anotherUnit(UUID id) {
        return c -> isUnit(c) && !c.getId().equals(id);
    }

    public static boolean isColorless(Card card) {
        return card.isColorless();
    }

    public static boolean isReady(Card card) {
        return !card.isDepleted();
    }

    public static Predicate<Card> isColor(Types.Knowledge color) {
        return c -> c.hasColor(color);
    }

    public static Predicate<Targetable> controlledBy(UUID controller) {
        return c -> isCard(c) && ((Card) c).controller.equals(controller);
    }

    public static Predicate<Targetable> anotherCard(UUID id) {
        return c -> isCard(c) && !c.getId().equals(id);
    }

    public static boolean isCard(Targetable o) {
        return (o instanceof Card);
    }

    public static boolean isPlayer(Targetable o) {
        return (o instanceof Player);
    }

    public static Predicate<Targetable> isDamagable(){
        return or(Predicates::isPlayer, Predicates::isUnit);
    }
}
