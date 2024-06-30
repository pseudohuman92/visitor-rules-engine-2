package com.visitor.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.UUIDHelper;
import com.visitor.protocol.ClientGameMessages;
import com.visitor.protocol.ClientGameMessages.*;
import com.visitor.protocol.ClientGameMessages.ClientGameMessage.PayloadCase;
import com.visitor.protocol.ServerGameMessages.ServerGameMessage;
import com.visitor.protocol.Types;
import com.visitor.protocol.Types.AttackerAssignment;
import com.visitor.protocol.Types.BlockerAssignment;
import com.visitor.protocol.Types.SelectFromType;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import static com.visitor.protocol.ClientGameMessages.ClientGameMessage.PayloadCase.*;
import static com.visitor.server.GeneralEndpoint.gameServer;
import static java.lang.System.out;
import static java.util.UUID.fromString;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

/**
 * @author pseudo
 */
@ServerEndpoint(value = "/games/{playerId}/{gameID}")
public class GameEndpoint implements GameEndpointInterface {
    Session session;
    UUID playerId;
    UUID gameID;
    boolean waitingResponse;
    PayloadCase responseType;
    SelectFromType selectFromType;


    @OnOpen
    public void onOpen(Session session, @PathParam("gameID") String gameID, @PathParam("playerId") String playerId) throws IOException, EncodeException {
        this.session = session;
        this.playerId = UUID.fromString(playerId);
        this.gameID = UUID.fromString(gameID);
        session.getBasicRemote().setBatchingAllowed(false);
        session.getAsyncRemote().setBatchingAllowed(false);
        session.setMaxIdleTimeout(0);
        gameServer.addGameConnection(this.gameID, this.playerId, this);
        resendLastMessage(this.playerId);
    }

    @OnMessage(maxMessageSize = 5242880)
    public void onMessage(Session session, byte[] message) {
        new Thread(() -> {
            try {
                ClientGameMessage cgm = ClientGameMessage.parseFrom(message);
                gameServer.appendToHistory(gameID, cgm);
                //writeToLog(cgm);
                //out.println("Received Message");
                //out.println(cgm);
                if (waitingResponse) {
                    processResponse(cgm);
                } else {
                    processMessage(cgm);
                }
            } catch (InvalidProtocolBufferException ex) {
                getLogger(GameEndpoint.class.getName()).log(SEVERE, null, ex);
            }
        }).start();
    }

    @OnClose
    public void onClose(Session session) {
        gameServer.removeGameConnection(gameID, playerId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        out.println("[ERROR: " + playerId + "] " + throwable.getMessage());
        throwable.printStackTrace();
    }

    public void send(ServerGameMessage.Builder builder, UUID targetId) throws IOException, EncodeException {
        ServerGameMessage message = builder.build();
        //writeToLog(message);
        gameServer.appendToHistory(gameID, message);
        checkResponseType(message);
        out.println("Sent Message");
        out.println(message);
        session.getBasicRemote().sendBinary(ByteBuffer.wrap(message.toByteArray()));
    }

    public void close() {
        session = null;
    }

    public void resendLastMessage(UUID targetId) throws IOException, EncodeException {
        ServerGameMessage lastMessage = gameServer.getLastMessage(gameID, playerId);
        if (lastMessage != null) {
            //out.println("Resending last message to "+playerId+" "+lastMessage);
            checkResponseType(lastMessage);
            session.getBasicRemote().sendObject(lastMessage.toByteArray());
        }
    }

    private void processResponse(ClientGameMessage message) {
        if (message.getPayloadCase() == CONCEDE) {
            gameServer.concede(gameID, playerId);
            return;
        }
        if (message.getPayloadCase() == responseType) {
            switch (message.getPayloadCase()) {
                case ORDERCARDSRESPONSE:
                    OrderCardsResponse ocr = message.getOrderCardsResponse();
                    waitingResponse = false;
                    gameServer.addToResponseQueue(gameID, ocr.getOrderedCardsList().toArray(new String[ocr.getOrderedCardsCount()]));
                    break;
                case SELECTFROMRESPONSE:
                    SelectFromResponse sfr = message.getSelectFromResponse();
                    if (sfr.getMessageType() != selectFromType) {
                        out.println("Wrong SelectFrom response received from " + playerId +
                                "\nExpected " + selectFromType + " Received: " + message);
                        break;
                    }
                    waitingResponse = false;
                    gameServer.addToResponseQueue(gameID, sfr.getSelectedList().toArray(new String[sfr.getSelectedCount()]));
                    break;
                case SELECTXVALUERESPONSE:
                    waitingResponse = false;
                    gameServer.addToResponseQueue(gameID, message.getSelectXValueResponse().getSelectedXValue());
                    break;
                case ASSIGNDAMAGERESPONSE:
                    ClientGameMessages.AssignDamageResponse ddr = message.getAssignDamageResponse();
                    waitingResponse = false;
                    gameServer.addToResponseQueue(gameID, ddr.getDamageAssignmentsList().toArray(new Types.DamageAssignment[ddr.getDamageAssignmentsCount()]));
                    break;
                case SELECTKNOWLEDGERESPONSE:
                    ClientGameMessages.SelectKnowledgeResponse skr = message.getSelectKnowledgeResponse();
                    waitingResponse = false;
                    gameServer.addToResponseQueue(gameID, skr.getSelectedKnowledge());
                    break;
                default:
                    out.println("Wrong Message received from " + playerId
                            + "\nExpected " + responseType + " Received: " + message);
                    break;
            }
            waitingResponse = false;
        } else {
            out.println("Unexpected response from " + playerId + ": " + message);
        }
    }

    private void processMessage(ClientGameMessage message) {
        Arraylist<Types.TargetSelection> targets = null;
        switch (message.getPayloadCase()) {
            case PLAYCARD:
                targets = new Arraylist<>(message.getPlayCard().getTargetsList().toArray(new Types.TargetSelection[message.getPlayCard().getTargetsCount()]));
                gameServer.playCard(gameID, playerId, fromString(message.getPlayCard().getCardId()), targets);
                break;
            case ACTIVATECARD:
                targets = new Arraylist<>(message.getActivateCard().getTargetsList().toArray(new Types.TargetSelection[message.getActivateCard().getTargetsCount()]));
                gameServer.activateCard(gameID, playerId, fromString(message.getActivateCard().getCardId()), fromString(message.getActivateCard().getAbilityId()), targets);
                break;
            case STUDYCARD:
                Types.Knowledge k = message.getStudyCard().getSelectedKnowledge();
                CounterMap<Types.Knowledge> km = new CounterMap<>(k, 1);
                if (k == Types.Knowledge.NONE) {
                    km = null;
                }
                gameServer.studyCard(gameID, playerId, fromString(message.getStudyCard().getCardId()), km);
                break;
            case PASS:
                gameServer.pass(gameID, playerId);
                break;
            case REDRAW:
                gameServer.redraw(gameID, playerId);
                break;
            case KEEP:
                gameServer.keep(gameID, playerId);
                break;
            case CONCEDE:
                gameServer.concede(gameID, playerId);
                break;
            case SELECTATTACKERS:
                SelectAttackers sar = message.getSelectAttackers();
                gameServer.selectAttackers(gameID, sar.getAttackersList().toArray(new AttackerAssignment[sar.getAttackersCount()]));
                break;
            case SELECTBLOCKERS:
                SelectBlockers sbr = message.getSelectBlockers();
                gameServer.selectBlockers(gameID, sbr.getBlockersList().toArray(new BlockerAssignment[sbr.getBlockersCount()]));
                break;
			/*
			case SAVEGAMESTATE:
				gameServer.saveGameState(gameID, message.getSaveGameState().getFilename());
				break;
			*/
            default:
                out.println("Unexpected message from " + playerId + ": " + message);
                break;
        }
    }

    private void checkResponseType(ServerGameMessage message) {
        waitingResponse = true;
        switch (message.getPayloadCase()) {
            case ORDERCARDS:
                responseType = ORDERCARDSRESPONSE;
                break;
            case SELECTFROM:
                responseType = SELECTFROMRESPONSE;
                selectFromType = message.getSelectFrom().getMessageType();
                break;
            case SELECTXVALUE:
                responseType = SELECTXVALUERESPONSE;
                break;
            case ASSIGNDAMAGE:
                responseType = ASSIGNDAMAGERESPONSE;
                break;
            case SELECTKNOWLEDGE:
                responseType = SELECTKNOWLEDGERESPONSE;
                break;
            default:
                waitingResponse = false;
                break;
        }
    }

	/*
	private void writeToLog (ClientGameMessage cgm) {
		try {
			new File("../game-logs/").mkdirs();
			writer = new BufferedWriter(new FileWriter("../game-logs/" + gameID.toString() + ".log", true));
			writer.append("[FROM: ").append(playerId).append("] ").append(String.valueOf(cgm));
			writer.flush();
			writer.close();
			writer = null;
		} catch (IOException ex) {
			getLogger(GameEndpoint.class.getName()).log(SEVERE, null, ex);
		}
	}

	private void writeToLog (ServerGameMessage message) {
		try {
			new File("../game-logs/").mkdirs();
			writer = new BufferedWriter(new FileWriter("../game-logs/" + gameID.toString() + ".log", true));
			writer.append("[TO: ").append(playerId).append("] ").append(String.valueOf(message));
			writer.flush();
			writer.close();
			writer = null;
		} catch (IOException ex) {
			getLogger(GameEndpoint.class.getName()).log(SEVERE, null, ex);
		}
	}
	*/

}
