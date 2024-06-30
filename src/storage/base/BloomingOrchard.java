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

import static com.visitor.card.properties.Combat.CombatAbility.Regenerate;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class BloomingOrchard extends Unit {

    public BloomingOrchard(Game game, UUID owner) {
        super(game, "Blooming Orchard",
                1, new CounterMap(YELLOW, 1),
                "{Use}: Gain 1 health.",
                0, 4,
                owner, Regenerate);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{Use}: Gain 1 health.",
                () -> game.gainHealth(controller, 1))
                .setDepleting());
    }
}
