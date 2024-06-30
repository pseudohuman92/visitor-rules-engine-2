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

import static com.visitor.card.properties.Combat.CombatAbility.Trample;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class GrizzlyBear extends Unit {

    public GrizzlyBear(Game game, UUID owner) {
        super(game, "Grizzly Bear",
                2, new CounterMap(GREEN, 2),
                "{Use}: Gain 1 energy.",
                3, 3,
                owner, Trample);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 0, "Gain 1 energy.",
                        () -> game.addEnergy(controller, 1)).setDepleting());
    }
}
