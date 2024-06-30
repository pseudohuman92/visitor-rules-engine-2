/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.protocol.Types.Knowledge.YELLOW;


/**
 * @author pseudo
 */
public class KineticReflectors extends Asset {

    public KineticReflectors(String owner) {
        super("Kinetic Reflectors", 2, new Hashmap(YELLOW, 2),
                "2, Activate:\n" +
                        "  Gain reflect 2.", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasEnergy(controller, 2);
    }

    @Override
    public void activate(Game game) {
        game.spendEnergy(controller, 2);
        game.deplete(id);
        game.addToStack(new AbilityCard(this, controller + " gains reflect 2",
                (x) -> {
                    game.addReflect(controller, 2);
                }));
    }
}
