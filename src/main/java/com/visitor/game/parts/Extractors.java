package com.visitor.game.parts;

import com.visitor.game.Card;
import com.visitor.game.Player;
import com.visitor.helpers.Arraylist;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Extractors extends Getters {
    public Card extractCard(UUID targetID) {
        for (Player player : players.values()) {
            Card c = player.extractCard(targetID);
            if (c != null) {
                c.zone = null;
                return c;
            }
        }
        for (Card c : stack) {
            if (c.getId().equals(targetID)) {
                stack.remove(c);
                c.zone = null;
                return c;
            }
        }
        return null;
    }

    private Arraylist<Card> extractAll(List<UUID> list) {
        return new Arraylist<>(list.stream().map(this::extractCard).collect(Collectors.toList()));
    }

    private Arraylist<Card> extractAllCopiesFrom(UUID playerId, String cardName, Game.Zone zone) {
        Arraylist<Card> cards = new Arraylist<>(((Arraylist<Card>) getZone(playerId, zone)).parallelStream()
                .filter(c -> c.name.equals(cardName)).collect(Collectors.toList()));
        getZone(playerId, zone).removeAll(cards);
        cards.forEach(c -> c.zone = null);
        return cards;
    }

    public Arraylist<com.visitor.game.Card> extractFromTopOfDeck(UUID playerId, int count) {
        return getPlayer(playerId).extractFromTopOfDeck(count);
    }

    public com.visitor.game.Card extractTopmostMatchingFromDeck(UUID playerId, Predicate<Card> cardPredicate) {
        return getPlayer(playerId).extractTopmostMatchingFromDeck(cardPredicate);
    }

}
