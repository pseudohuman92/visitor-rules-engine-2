package com.visitor.game.parts;

import com.visitor.protocol.Types;

import java.util.UUID;

public class PlayerDelegators extends CardDelegators {
    public void shuffleIntoDeck(UUID playerId, com.visitor.game.Card... cards) {
        getPlayer(playerId).shuffleIntoDeck(cards);
    }

    public int getPlayerEnergy(UUID playerId) {
        return getPlayer(playerId).getEnergy();
    }

    public int getMaxEnergy(UUID playerId) {
        return getPlayer(playerId).getMaxEnergy();
    }

    public boolean hasMaxEnergy(UUID playerId, int count) {
        return getPlayer(playerId).getMaxEnergy() >= count;
    }


    public void shuffleDeck(UUID playerId) {
        getPlayer(playerId).shuffleDeck();
    }


    public int getKnowledgeCount(UUID playerId, Types.Knowledge knowledge) {
        return getPlayer(playerId).getKnowledgeCount(knowledge);
    }

    public boolean hasHealth(UUID playerId, int i) {
        return getPlayer(playerId).hasHealth(i);
    }

    public int getDeckSize(UUID playerId) {
        return getPlayer(playerId).deck.size();
    }

    public void addEnergy(UUID playerId, int i, boolean turnly) {
            getPlayer(playerId).addEnergy(i, turnly);
    }

    public void removeEnergy(UUID playerId, int i) {
        getPlayer(playerId).removeEnergy(i);
    }

    public void removeMaxEnergy(UUID playerId, int count) {
        getPlayer(playerId).removeMaxEnergy(count);
    }

    public void loseHealth(UUID playerId, int count) {
        getPlayer(playerId).payHealth(count);
    }

    public void addStudyCount(UUID playerId, int count) {
        getPlayer(playerId).addStudyCount(count);
    }

    public void gainHealth(UUID playerId, int health) {
        getPlayer(playerId).addHealth(health);
    }
}
