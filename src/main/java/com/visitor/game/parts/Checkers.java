package com.visitor.game.parts;

import com.visitor.card.properties.Targetable;
import com.visitor.game.Card;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types;

import java.util.UUID;
import java.util.function.Predicate;

import static com.visitor.protocol.Types.Phase.*;

public class Checkers extends Putters {

    public boolean hasIn(UUID playerId, Zone zone, Predicate<Targetable> validTarget, int count) {
        return getZone(playerId, zone).parallelStream().filter(validTarget).count() >= count;
    }

    public boolean hasInWithPlayers(UUID playerId, Zone zone, Predicate<Targetable> validTarget, int count) {
        Arraylist<Targetable> a = new Arraylist<>();
        a.addAll(getZone(playerId, zone));
        a.addAll(getAllPlayers(validTarget));
        return a.parallelStream().filter(validTarget).count() >= count;
    }

    public boolean isIn(UUID playerId, Zone zone, UUID cardID) {
        return getZone(playerId, zone).parallelStream().anyMatch(getCard(cardID)::equals);
    }

    public boolean hasEnergy(UUID playerId, int i) {
        return getPlayer(playerId).getEnergy() >= i;
    }

    public boolean hasKnowledge(UUID playerId, CounterMap<Types.Knowledge> knowledge) {
        return getPlayer(playerId).hasKnowledge(knowledge);
    }

    public boolean canPlaySlow(UUID playerId) {
        return turnPlayer.equals(playerId)
                && activePlayer.equals(playerId)
                && stack.isEmpty()
                && (phase == MAIN_BEFORE || phase == MAIN_AFTER);
    }

    public boolean canPlayFast(UUID playerId) {
        return activePlayer.equals(playerId)
                && (phase == MAIN_BEFORE ||
                phase == ATTACK_PLAY ||
                phase == BLOCK_PLAY ||
                phase == MAIN_AFTER);
    }


    public boolean canStudy(UUID playerId) {
        return canPlaySlow(playerId)
                && getPlayer(playerId).hasStudy(1);
    }

    public boolean canAttack(UUID playerId) {
        return turnPlayer.equals(playerId)
                && activePlayer.equals(playerId)
                && (phase == ATTACK);
    }

    public boolean canBlock(UUID playerId) {
        return !turnPlayer.equals(playerId)
                && activePlayer.equals(playerId)
                && (phase == BLOCK);
    }

    public boolean isPlayerActive(UUID playerId) {
        return activePlayer.equals(playerId);
    }

    public boolean isPlayerInGame(String username) {
        return getPlayerId(username) != null;
    }

    public boolean isTurnPlayer(UUID playerId) {
        return turnPlayer.equals(playerId);
    }

    public boolean isPlayer(UUID targetId) {
        return players.values().stream().anyMatch(p -> p.getId().equals(targetId));
    }

    public boolean controlledByOpponent(UUID targetID) {
        com.visitor.game.Card c = getCard(targetID);
        return c.controller.equals(this.getOpponentId(c.controller));
    }

    public boolean controlsUnownedCard(UUID playerId, Zone zone) {
        return getZone(playerId, zone).parallelStream().anyMatch(c -> controlledByOpponent(c.getId()));
    }

}
