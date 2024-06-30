/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Blitz;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class DeploymentTruck extends Unit {

    public DeploymentTruck(Game game, UUID owner) {
        super(game, "Deployment Truck",
                3, new CounterMap(RED, 2),
                "When {~} dies, create 3 Stormtroopers",
                3, 3,
                owner, Blitz);
        triggering.addEventChecker(EventChecker.selfDeathChecker(game, this, () -> {
            for (int i = 0; i < 3; i++){
                new Stormtrooper(game, controller).resolve();
            }
        }));
    }
}
