/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Haste;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class CreepingCanopyVine extends Unit {

    public CreepingCanopyVine(Game game, UUID owner) {
        super(game, "Creeping Canopy Vine",
                2, new CounterMap(RED, 2),
                "{R}{R}{R} - {3}: {~} gains +1/+1.\n" +
                        "{Use}: Deal X damage to any target where X is {~}'s health. {~} becomes 1/1",
                3, 1,
                owner, Haste);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 3, "{R}{R}{R} - {3}: {~} gains +1/+1.",
                () -> game.runIfInZone(controller, Game.Zone.Play, id, () -> game.addAttackAndHealth(id, 1, 1)))
                .setKnowledgeRequirement(new CounterMap<>(RED, 3)));

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{Use}: Deal X damage to any target where X is {~}'s health. {~} becomes 1/1")
                .setTargetingForDamage(targetId -> {
                    game.dealDamage(id, targetId, game.getHealth(id));
                    game.runIfInPlay(id, () -> game.setAttackAndHealth(id, 1, 1));
                })
                .setDepleting());
    }
}
