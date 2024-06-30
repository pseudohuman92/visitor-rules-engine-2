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

import static com.visitor.protocol.Types.Knowledge.BLACK;


/**
 * @author pseudo
 */
public class Thief extends Asset {

    public Thief(String owner) {
        super("Thief", 1, new Hashmap(BLACK, 1),
                "Activate: Loot 1", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.isActive(controller) && game.hasEnergy(controller, 1) && (!depleted);
    }

    @Override
    public void activate(Game game) {
        game.deplete(id);
        game.addToStack(new AbilityCard(this, controller + " loots 1",
                (x) -> {
                    game.loot(controller, 1);
                }));
    }
}
