package com.visitor.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.visitor.protocol.ClientGameMessages.ClientGameMessage;
import com.visitor.protocol.ServerGameMessages.ServerGameMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;

import static com.visitor.protocol.ClientGameMessages.ClientGameMessage.PayloadCase.PICKCARDRESPONSE;
import static com.visitor.server.GeneralEndpoint.gameServer;
import static java.lang.System.out;
import static java.util.UUID.fromString;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

/**
 * @author pseudo
 */
@ServerEndpoint(value = "/drafts/{playerId}/{draftID}")
public class DraftEndpoint {

    Session session;
    UUID playerId;
    UUID draftID;


    @OnOpen
    public void onOpen(Session session, @PathParam("playerId") String playerId, @PathParam("draftID") String gameID) throws IOException, EncodeException {
        this.session = session;
        this.playerId = UUID.fromString(playerId);
        this.draftID = UUID.fromString(gameID);
        session.getBasicRemote().setBatchingAllowed(false);
        session.getAsyncRemote().setBatchingAllowed(false);
        session.setMaxIdleTimeout(0);
        gameServer.addDraftConnection(this.draftID, this.playerId, this);
        resendLastMessage();
    }

    @OnMessage
    public void onMessage(Session session, byte[] message) {
        new Thread(() -> {
            try {
                ClientGameMessage cgm = ClientGameMessage.parseFrom(message);
                //out.println("Received from " + playerId + "\n" + message);
                processMessage(cgm);
            } catch (InvalidProtocolBufferException ex) {
                getLogger(DraftEndpoint.class.getName()).log(SEVERE, null, ex);
            }
        }).start();
    }

    @OnClose
    public void onClose(Session session) {
        gameServer.removeDraftConnection(draftID, playerId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        out.println("[ERROR: " + playerId + "] " + throwable.getMessage());
        throwable.printStackTrace();
    }

    public void send(ServerGameMessage.Builder builder) throws IOException, EncodeException {
        ServerGameMessage message = builder.build();
        //out.println("Sending: " + message);
        session.getBasicRemote().sendObject(message.toByteArray());
    }

    public void close() {
        session = null;
    }

    public void resendLastMessage() throws IOException, EncodeException {
        ServerGameMessage lastMessage = gameServer.getDraftLastMessage(draftID, playerId);
        if (lastMessage != null) {
            session.getBasicRemote().sendObject(lastMessage.toByteArray());
        }
    }


    private void processMessage(ClientGameMessage message) {
        if (message.getPayloadCase() == PICKCARDRESPONSE) {
            gameServer.draftPick(draftID, playerId, fromString(message.getPickCardResponse().getPickedCard()));
        } else {
            out.println("Unexpected message from " + playerId + ": " + message);
        }
    }
}
