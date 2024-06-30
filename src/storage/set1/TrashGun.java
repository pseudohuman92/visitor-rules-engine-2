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

import java.util.UUID;

import static com.visitor.protocol.Types.Counter.CHARGE;
import static com.visitor.protocol.Types.Knowledge.BLUE;


/**
 * @author pseudo
 */
public class TrashGun extends Asset {

    public TrashGun(GarbageWeapon c) {
        super("Trash Gun", 0, new Hashmap(BLUE, 1),
                "Discharge 1, Activate: \n" +
                        "  Deal 2 damage. \n" +
                        "  If ~ has no counters on it, transform ~ to Garbage Weapon", c.controller);
        copyPropertiesFrom(c);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return counters.get(CHARGE) > 0;
    }

    @Override
    public void activate(Game game) {
        game.deplete(id);
        removeCounters(CHARGE, 1);
        UUID target = game.selectDamageTargets(controller, 1, false).get(0);
        game.addToStack(new AbilityCard(this,
                "Deal 2 damage\n" +
                        "If ~ has no counters on it, transform ~ to UI08",
                (x) -> {
                    game.dealDamage(id, target, 2);
                    if (counters.get(CHARGE) == 0) {
                        game.transformTo(this, this, new GarbageWeapon(this));
                    }
                }));
    }
}
