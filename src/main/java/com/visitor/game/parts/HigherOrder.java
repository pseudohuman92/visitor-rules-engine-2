package com.visitor.game.parts;

import com.visitor.card.properties.Targetable;
import com.visitor.game.Card;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.visitor.game.parts.Base.Zone.Both_Play;

public class HigherOrder extends Checkers {
    public void forEachInZone(UUID playerId, Zone zone, Predicate<Targetable> filter, Consumer<UUID> cardIdConsumer) {
        getZone(playerId, zone).forEach(card -> {
            if (filter.test(card)) {
                cardIdConsumer.accept(card.getId());
            }
        });
    }

    public int countInZone(UUID playerId, Zone zone, Predicate<Targetable> cardConsumer) {
        Arraylist<Integer> count = new Arraylist<>();
        getZone(playerId, zone).forEach(card -> {
            if (cardConsumer.test(card)) {
                count.add(0);
            }
        });
        return count.size();
    }

    public void runIfInZone(UUID playerId, Zone zone, UUID cardId, Runnable function) {
        if (isIn(playerId, zone, cardId)) {
            function.run();
        }
    }

    public void runIfHasKnowledge(UUID playerId, CounterMap<Types.Knowledge> knowledge, Runnable effect) {
        if (getPlayer(playerId).hasKnowledge(knowledge)) {
            effect.run();
        }
    }

    public void runIfInPlay(UUID cardId, Runnable r) {
        runIfInZone(UUID.randomUUID(), Both_Play, cardId, r);
    }
}
