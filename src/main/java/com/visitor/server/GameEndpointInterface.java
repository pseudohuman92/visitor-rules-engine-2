package com.visitor.server;

import com.visitor.protocol.ServerGameMessages;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.UUID;

public interface GameEndpointInterface {

    void send(ServerGameMessages.ServerGameMessage.Builder builder, UUID targetId) throws IOException, EncodeException;

    void close();

    void resendLastMessage(UUID targetId) throws IOException, EncodeException;

}
