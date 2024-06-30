package com.visitor.helpers;

import com.visitor.game.Card;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.fromString;

/**
 * @author pseudo
 */
public abstract class UUIDHelper {
    /**
     * Extracts the uuids of the given list of cards while preserving their order.
     *
     * @param cards
     * @return
     */
    public static Arraylist<UUID> toUUIDList(Arraylist<Card> cards) {
        Arraylist<UUID> uuids = new Arraylist<>();
        while (!cards.isEmpty()) {
            Card c = cards.remove(0);
            uuids.add(c.getId());
        }
        return uuids;
    }

    public static Arraylist<UUID> toUUIDList(String[] cards) {
        Arraylist<UUID> uuids = new Arraylist<>();
        for (String card : cards) {
            uuids.add(fromString(card));
        }
        return uuids;
    }

    public static Arraylist<UUID> toUUIDList(List<String> cards) {
        Arraylist<UUID> uuids = new Arraylist<>();
        for (String card : cards) {
            uuids.add(fromString(card));
        }
        return uuids;
    }

    /**
     * Sorts given list of cards in the order of the provided uuids.
     * If a card appears in the card list but not in id list, card is ignored.
     * If a id appears in id list but there is no card corresponding to it in the card list, id is ignored.
     *
     * @param cards
     * @param uuids
     * @return
     */
    public static Arraylist<Card> sortByID(Arraylist<Card> cards, Arraylist<UUID> uuids) {
        Arraylist<Card> sorted = new Arraylist<>();
        while (!uuids.isEmpty()) {
            UUID u = uuids.remove(0);
            for (Card c : cards) {
                if (c.getId().equals(u)) {
                    sorted.add(c);
                    break;
                }
            }
        }
        return sorted;
    }


    public static Arraylist<Card> getInList(Arraylist<Card> cards, Arraylist<UUID> uuids) {
        Arraylist<Card> selected = new Arraylist<>();
        for (Card card : cards) {
            if (uuids.contains(card.getId())) {
                selected.add(card);
            }
        }
        return selected;
    }

    public static Arraylist<Card> getNotInList(Arraylist<Card> cards, Arraylist<UUID> uuids) {
        Arraylist<Card> selected = new Arraylist<>();
        for (Card card : cards) {
            if (!uuids.contains(card.getId())) {
                selected.add(card);
            }
        }
        return selected;
    }
}
