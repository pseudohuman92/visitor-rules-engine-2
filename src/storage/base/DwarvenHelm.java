/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.*;

/**
 * @author pseudo
 */
public class DwarvenHelm extends Unit {

    public DwarvenHelm(Game game, UUID owner) {
        super(game, "Dwarven Helm",
                3, new CounterMap(RED, 1),
                "{P}{P} - {2}, {Use}: Target opponent loses 2 health and you gain 2 health.\n" +
                        "{G}{G} - {2}, {Use}: Units you control gain +2/+2 until end of turn.",
                3, 2,
                owner);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 2, "{2}, {Use}: Target opponent loses 2 health and you gain 2 health.",
                () -> {
                    game.payHealth(game.getOpponentId(controller), 2);
                    game.gainHealth(controller, 2);
                })
                .setDepleting()
                .setKnowledgeRequirement(new CounterMap<>(PURPLE, 2)));

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 2, "{2}, {Use}: Units you control gain +2/+2 until end of turn.",
                () ->
                        game.forEachInZone(controller, Game.Zone.Play, Predicates::isUnit,
                                targetId -> game.addTurnlyAttackAndHealth(targetId, 2, 2))
        )
                .setDepleting()
                .setKnowledgeRequirement(new CounterMap<>(GREEN, 2)));
    }
}
