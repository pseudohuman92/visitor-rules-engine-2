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
public class Elephant extends Unit {

    public Elephant(Game game, UUID owner) {
        super(game, "Elephant",
                4, new CounterMap(GREEN, 3),
                "{Use}: Gain 3 energy.",
                6, 6,
                owner, Trample);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 0, "Gain 3 energy.",
                        () -> game.addEnergy(controller, 3)).setDepleting());
    }
}
