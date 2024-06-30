package com.visitor.server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.visitor.game.Card;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.HelperFunctions;
import com.visitor.protocol.ClientMessages.ClientMessage;
import com.visitor.protocol.ClientMessages.JoinQueue;
import com.visitor.protocol.ServerMessages;
import com.visitor.protocol.ServerMessages.ServerMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import static java.lang.System.out;

@ServerEndpoint(value = "/profiles/{playerId}")
public class GeneralEndpoint {

    public static GameServer gameServer = null;
    private Session session;
    private String playerName;

    @OnOpen
    public void onOpen(Session session, @PathParam("playerId") String playerName) throws IOException {
        out.println(playerName + " connected!");
        this.session = session;
        this.playerName = playerName;

        session.getBasicRemote().setBatchingAllowed(false);
        session.getAsyncRemote().setBatchingAllowed(false);
        session.setMaxIdleTimeout(0);
        session.setMaxBinaryMessageBufferSize(65536 * 16);
        if (gameServer == null) {
            gameServer = new GameServer();
        }
        out.println(playerName + " connected!");
        gameServer.addConnection(playerName, this);
    }

    @OnMessage
    public void onMessage(Session session, byte[] message) throws IOException {
        ClientMessage cm = ClientMessage.parseFrom(message);
        out.println(playerName + " sent a message: " + cm + "with buffersize" + session.getMaxBinaryMessageBufferSize());
        handleMessage(cm);
    }

    @OnClose
    public void onClose(Session session) {
        out.println(playerName + " disconnected!");
        gameServer.removeConnection(playerName);
        this.session = null;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        out.println("General " + playerName + " ERROR!");
        throwable.printStackTrace();
    }

    public void send(ServerMessage message) throws IOException, EncodeException {
        out.println("Server sending a message to " + playerName + ": " + message + "with buffer size " + session.getMaxBinaryMessageBufferSize());
        session.getBasicRemote().sendBinary(ByteBuffer.wrap(message.toByteArray()));
    }

    public void send(ServerMessage.Builder builder) throws IOException, EncodeException {
        ServerMessage message = builder.build();
        out.println("Server sending a message to " + playerName + ": " + message + "with buffer size " + session.getMaxBinaryMessageBufferSize());
        session.getBasicRemote().sendBinary(ByteBuffer.wrap(message.toByteArray()));
    }

    private void handleMessage(ClientMessage cm) {
        switch (cm.getPayloadCase()) {
            case JOINQUEUE:
                JoinQueue jqm = cm.getJoinQueue();
                String draftId = jqm.getDraftId();
                gameServer.joinQueue(playerName, jqm.getGameType(), jqm.getDecklistList().toArray(new String[jqm.getDecklistCount()]),
                        draftId.equals("") ? null : UUID.fromString(draftId));
                break;
            case GETCOLLECTION:
                out.println("Gotcollection");
                ServerMessages.GetCollectionResponse.Builder c = ServerMessages.GetCollectionResponse.newBuilder();
                c.addAllCards(HelperFunctions.getCollection().transform(x -> x.toCardMessage().build()));
                ServerMessage.Builder b = ServerMessage.newBuilder();
                b.setGetCollectionResponse(c);
                try {
                    send(b);
                } catch (IOException | EncodeException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                out.println("Some other" + cm.toString());
        }
    }
}
