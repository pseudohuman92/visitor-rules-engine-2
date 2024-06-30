package com.visitor.game;

import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.protocol.ClientGameMessages.*;
import com.visitor.protocol.ServerGameMessages.*;
import com.visitor.protocol.Types;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class AiPlayer extends Player {

    final static String[] decklist = {
            "3;base.Eagle",
            "3;base.Hawk",
            "3;base.Leech",
            "3;base.Owl",
            "3;base.Ox",
            "3;base.Pistol Shrimp",
            "3;base.Porcupine",
            "3;base.Seagull",
            "3;base.Sparrow",
            "3;base.Turtle"};

    public AiPlayer(Game game) {
        super(game, "AI Player", decklist);
    }

    public static ClientGameMessage getResponse(ServerGameMessage message, UUID aiId) {
        switch (message.getPayloadCase()) {
            case PICKCARD:
                PickCard pickCard = message.getPickCard();
                //Draft option
                return null;

            case ORDERCARDS:
                OrderCards orderCards = message.getOrderCards();
                Arraylist<Types.CardP> orderedCards = new Arraylist<>(orderCards.getCardsToOrderList());
                Arraylist<String> orderedIds = new Arraylist<>();

                while (!orderedCards.isEmpty()) {
                    orderedIds.add(orderedCards.remove(getRandomInt(orderedCards.size())).getId());
                }

                return ClientGameMessage.newBuilder().setOrderCardsResponse(
                        OrderCardsResponse.newBuilder()
                                .addAllOrderedCards(orderedIds)
                                .build()).build();

            case SELECTFROM:
                SelectFrom selectFrom = message.getSelectFrom();

                int selectCount =
                        selectFrom.getTargets().getMinTargets() +
                                getRandomInt(selectFrom.getTargets().getMaxTargets() - selectFrom.getTargets().getMinTargets()  + 1);

                Arraylist<String> selectedIds = new Arraylist<>();
                Arraylist<String> selectables = new Arraylist<>(selectFrom.getTargets().getPossibleTargetsList());

                for (int i = 0; i < selectCount; i++) {
                    int index = getRandomInt(selectables.size());
                    selectedIds.add(selectables.remove(index));
                }

                return ClientGameMessage.newBuilder().setSelectFromResponse(
                        SelectFromResponse.newBuilder()
                                .setMessageType(selectFrom.getMessageType())
                                .addAllSelected(selectedIds)
                                .build()).build();

            case ASSIGNDAMAGE:
                AssignDamage assignDamage = message.getAssignDamage();
                Types.GameStateP gameState = assignDamage.getGame();

                Arraylist<Types.DamageAssignment> damageAssignments = new Arraylist<>();
                int totalDamage = assignDamage.getTotalDamage();
                Arraylist<String> targets = new Arraylist<>(assignDamage.getPossibleTargetsList());

                while (totalDamage > 0) {
                    //TODO: Implement here.
                    break;
                }

                return null;

            case SELECTXVALUE:
                SelectXValue selectXValue = message.getSelectXValue();
                return ClientGameMessage.newBuilder().setSelectXValueResponse(
                        SelectXValueResponse.newBuilder()
                                .setSelectedXValue(
                                        getRandomInt(selectXValue.getMaxXValue() + 1))
                                .build()).build();
                /*
            case SELECTBLOCKERS:
                SelectBlockers selectBlockers = message.getSelectBlockers();
                Arraylist<Types.Blocker> blockers = new Arraylist<>(selectBlockers.getPossibleBlockersList());
                Arraylist<Types.BlockerAssignment> blockerAssignments = new Arraylist<>();

                for (Types.Blocker b : blockers) {
                    if (ThreadLocalRandom.current().nextBoolean()) { //choose if blocks
                        blockerAssignments.add(
                                Types.BlockerAssignment.newBuilder()
                                        .setBlockerId(b.getBlockerId())
                                        .setBlockedBy(b.getPossibleBlockTargets(
                                                getRandomInt(b.getPossibleBlockTargetsCount())))
                                        .build());
                    }
                }

                return ClientGameMessage.newBuilder().setSelectBlockersResponse(
                        SelectBlockersResponse.newBuilder()
                                .addAllBlockers(blockerAssignments)
                                .build()).build();

            case SELECTATTACKERS:
                SelectAttackers selectAttackers = message.getSelectAttackers();
                Arraylist<Types.Attacker> attackers = new Arraylist<>(selectAttackers.getPossibleAttackersList());
                Arraylist<Types.AttackerAssignment> attackerAssignments = new Arraylist<>();

                for (Types.Attacker a : attackers) {
                    if (ThreadLocalRandom.current().nextBoolean()) { //choose if attacks
                        attackerAssignments.add(
                                Types.AttackerAssignment.newBuilder()
                                        .setAttackerId(a.getAttackerId())
                                        .setAttacksTo(a.getPossibleAttackTargets(
                                                getRandomInt(a.getPossibleAttackTargetsCount())))
                                        .build());
                    }
                }

                return ClientGameMessage.newBuilder().setSelectAttackersResponse(
                        SelectAttackersResponse.newBuilder()
                                .addAllAttackers(attackerAssignments)
                                .build()).build();
               */
            case SELECTKNOWLEDGE:
                SelectKnowledge selectKnowledge = message.getSelectKnowledge();

                return ClientGameMessage.newBuilder().setSelectKnowledgeResponse(
                        SelectKnowledgeResponse.newBuilder()
                                .setSelectedKnowledge(selectKnowledge.getKnowledgeList(
                                        getRandomInt(selectKnowledge.getKnowledgeListCount())))
                                .build()).build();

            case UPDATEGAMESTATEP:
                ClientGameMessage.Builder builder = ClientGameMessage.newBuilder();
                gameState = message.getUpdateGameStateP().getGame();
                if (gameState.getActivePlayer().equals(aiId.toString())) {
                    if (gameState.getPhase() == Types.Phase.REDRAW) {
                        return builder.setKeep(Keep.newBuilder().build()).build();
                    } else if (gameState.getPhase() == Types.Phase.ATTACK) {
                        return builder.setSelectAttackers(SelectAttackers.newBuilder().build()).build();
                    } else if (gameState.getPhase() == Types.Phase.BLOCK) {
                        return builder.setSelectBlockers(SelectBlockers.newBuilder().build()).build();
                    } else

                    /*
                    if (gameState.getCanStudyCount() > 0) {
                        return builder.setStudyCard(
                                StudyCard.newBuilder()
                                        .setCardID(gameState.getCanStudy(
                                                getRandomInt(gameState.getCanStudyCount())))
                                        .build()).build();
                    }

                    if (gameState.getCanPlayCount() > 0) {
                        return builder.setPlayCard(
                                PlayCard.newBuilder()
                                        .setCardID(gameState.getCanPlay(
                                                getRandomInt(gameState.getCanPlayCount())))
                                        .build()).build();
                    }

                    if (gameState.getCanActivateCount() > 0) {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            return builder.setActivateCard(
                                    ActivateCard.newBuilder()
                                            .setCardID(gameState.getCanActivate(
                                                    getRandomInt(gameState.getCanActivateCount())))
                                            .build()).build();
                        } else {
                            return builder.setPass(Pass.newBuilder()).build();
                        }
                    }

                     */
                    return builder.setPass(Pass.newBuilder()).build();
                }
                return null;

            case GAMEEND:
                return null;

            default:
                System.out.println("Unhandled message type in AI:\n" + message);
                return null;
        }
    }

    public static int getRandomInt(int maxExclusive) {
        return ThreadLocalRandom.current().nextInt(0, maxExclusive);
    }
}
