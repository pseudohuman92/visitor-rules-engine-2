/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class PowerPulse extends Spell {

    public PowerPulse(String owner) {
        super("Power Pulse", 4, new Hashmap(GREEN, 2),
                "Deal 1 damage to a target for every 2 maximum energy you have.", owner);
    }

    protected void beforePlay(Game game) {
        targets = game.selectDamageTargets(controller, 1, false);
    }

    @Override
    protected void duringResolve(Game game) {
        game.dealDamage(id, targets.get(0), game.getMaxEnergy(controller) / 2);
    }
}

