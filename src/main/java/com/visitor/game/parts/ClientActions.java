package com.visitor.game.parts;

import com.visitor.game.Card;
import com.visitor.game.Event;
import com.visitor.helpers.Arraylist;

import java.util.UUID;

import static java.lang.System.out;
import static java.util.UUID.fromString;

import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types;
import com.visitor.protocol.Types.AttackerAssignment;
import com.visitor.protocol.Types.BlockerAssignment;

public class ClientActions extends Turns {
    /**
     * Game Action Methods
     * These are game actions taken by a client
     */

    public void playCard(UUID playerId, UUID cardID, boolean withCost, Arraylist<Types.TargetSelection> targets) {
        Card card = extractCard(cardID);
        card.setPlayableTargets(targets);
        card.play(withCost);
        addEvent(Event.playCard(card), true);
        activePlayer = getOpponentId(playerId);
    }

    public void playCard(UUID playerId, UUID cardID, Arraylist<Types.TargetSelection>  targets) {
        playCard(playerId, cardID, true, targets);
    }

    public void playCardWithoutCost(UUID playerId, UUID cardID, Arraylist<Types.TargetSelection>  targets) {
        playCard(playerId, cardID, false, targets);
    }

    public void activateCard(UUID playerId, UUID cardId, UUID abilityId, Arraylist<Types.TargetSelection>  targets) {
        Card card = getCard(cardId);
        card.setActivatableTargets(abilityId, targets);
        card.activate(abilityId);
        activePlayer = getOpponentId(playerId);
    }

    public void studyCard(UUID playerId, UUID cardID, boolean regular, CounterMap<Types.Knowledge> knowledge) {
        Card c = extractCard(cardID);
        c.study(getPlayer(playerId), regular, knowledge);
        addEvent(Event.study(playerId, c), regular);
    }

    public void pass(UUID playerId) {
        passCount++;
        if (passCount == 2) {
            if (!stack.isEmpty()) {
                resolveStack();
            } else {
                changePhase();
            }
        } else {
            activePlayer = this.getOpponentId(playerId);
        }
    }

    public void redraw(UUID playerId) {
        getPlayer(playerId).redraw();
    }

    public void keep(UUID playerId) {
        passCount++;
        if (passCount == 2) {
            changePhase();
        } else {
            activePlayer = this.getOpponentId(playerId);
        }
    }

    public void selectAttackers(AttackerAssignment[] attackers) {
        Arraylist<AttackerAssignment> attackerIds = new Arraylist<>(attackers);
        out.println("Attackers: " + attackerIds);
        Arraylist<Card> attackingCards = new Arraylist<>();
        attackerIds.forEach(c -> {
            Card u = getCard(fromString(c.getAttackerId()));
            u.setAttacking(fromString(c.getAttacksTo()));
            this.attackers.add(u.getId());
            attackingCards.add(u);
        });
        addEvent(Event.attack(attackingCards), true);
        changePhase();
    }

    public void selectBlockers(BlockerAssignment[] blockers) {
        Arraylist<BlockerAssignment> assignedBlockers = new Arraylist<>(blockers);
        out.println("Blockers: " + assignedBlockers);
        assignedBlockers.forEach(c -> {
            UUID blockerId = fromString(c.getBlockerId());
            UUID blockedBy = fromString(c.getBlockedBy());

            getCard(blockedBy).addBlocker(blockerId);
            Card blocker = getCard(blockerId);
            blocker.setBlocking(blockedBy);
            this.blockers.add(blockerId);
        });
        changePhase();
    }
}
