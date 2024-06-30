package com.visitor.game.parts;

import java.util.UUID;

import static com.visitor.protocol.Types.Phase.*;
import static java.lang.System.out;

public class Turns extends Actions {
    /**
     * Turn Structure Methods
     * These are the methods that handle turn / phase transitions.
     */
    @SuppressWarnings("DuplicateBranchesInSwitch")
    public void changePhase() {
        out.println("Changing Phase from " + phase);
        passCount = 0;
        activePlayer = turnPlayer;
        switch (phase) {
            case REDRAW:
                phase = BEGIN;
                newTurn();
                break;
            case BEGIN:
                phase = MAIN_BEFORE;
                break;
            case MAIN_BEFORE:
                activePlayer = turnPlayer;
                phase = ATTACK;
                break;
            case ATTACK:
                phase = ATTACK_PLAY;
                activePlayer = turnPlayer;
                if (attackers.isEmpty()) {
                    phase = MAIN_AFTER;
                }
                break;
            case ATTACK_PLAY:
                activePlayer = this.getOpponentId(turnPlayer);
                phase = BLOCK;
                break;
            case BLOCK:
                activePlayer = turnPlayer;
                phase = BLOCK_PLAY;
                break;
            case BLOCK_PLAY:
                dealCombatDamage();
                unsetAttackers();
                unsetBlockers();
                phase = MAIN_AFTER;
                break;
            case MAIN_AFTER:
                phase = END;
                endTurn();
                break;
            case END:
                phase = BEGIN;
                newTurn();
                break;
        }
    }

    private void endTurn() {
        processEndEvents();
        players.values().forEach(com.visitor.game.Player::endTurn);
        if (getPlayer(turnPlayer).getHandSize() > 7) {
            discard(turnPlayer, getPlayer(turnPlayer).hand.size() - 7);
        }
        processEvents();
        changePhase();
    }

    private void newTurn() {
        if (turnCount > 0) {
            turnPlayer = this.getOpponentId(turnPlayer);
            getPlayer(turnPlayer).draw(1);
        }
        activePlayer = turnPlayer;
        passCount = 0;
        getPlayer(turnPlayer).draw(1);
        getPlayer(turnPlayer).newTurn();
        turnCount++;
        processBeginEvents();
        changePhase();
    }
}
