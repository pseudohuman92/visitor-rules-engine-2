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

import static com.visitor.card.properties.Combat.CombatAbility.Deathtouch;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class Spider extends Unit {

    UUID target;

    public Spider(Game game, UUID owner) {
        super(game, "Spider",
                1, new CounterMap(GREEN, 1),
                "{G}{G}{G}- {4}, {Use}: Target unit gains +X/+X until end of turn where X is the number of {G} you have.",
                1, 1,
                owner, Deathtouch);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 4, "Target unit gains +X/+X until end of turn where X is the number of {G} you have.",
                        () -> target = game.selectFromZone(controller, Game.Zone.Both_Play, Predicates::isUnit, 1, 1, "Select a unit").get(0),
                        () -> game.runIfInZone(controller, Game.Zone.Both_Play, target,
                                () -> {
                                    int x = game.getKnowledgeCount(controller, GREEN);
                                    game.addTurnlyAttackAndHealth(target, x, x);
                                })).
                        setDepleting()
                        .setKnowledgeRequirement(new CounterMap<>(GREEN, 3)));
    }
}
