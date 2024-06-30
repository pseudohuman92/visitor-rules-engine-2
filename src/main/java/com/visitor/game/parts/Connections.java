package com.visitor.game.parts;

import com.visitor.protocol.ServerGameMessages.ServerGameMessage;
import com.visitor.server.GameEndpointInterface;

import java.util.UUID;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

public class Connections extends HigherOrder {
    /**
     * Connection Methods
     * To deal with client connections
     */
    public void addConnection(UUID playerId, GameEndpointInterface connection) {
        connections.putIn(playerId, connection);
    }

    public void removeConnection(UUID playerId) {
        connections.removeFrom(playerId);
    }

    public void setLastMessage(UUID playerId, ServerGameMessage lastMessage) {
        lastMessages.put(playerId, lastMessage);
    }

    public ServerGameMessage getLastMessage(UUID playerId) {
        return lastMessages.get(playerId);
    }

    public void addToResponseQueue(Object o) {
        try {

            response.put(o);
        } catch (InterruptedException ex) {
            getLogger(Game.class.getName()).log(SEVERE, null, ex);
        }
    }


}
