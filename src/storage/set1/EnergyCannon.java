/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.card.types.helpers.ActivatedAbility;
import com.visitor.card.types.specialtypes.ActivatableAsset;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;


/**
 * @author pseudo
 */
public class EnergyCannon extends ActivatableAsset {

    public EnergyCannon(Game game, String owner) {
        super(game, "Energy Cannon", 7, new Hashmap(GREEN, 4),
                "X, Activate: \n" +
                        "    Deal X damage.",
                owner);

        activatable.addActivatedAbility(new ActivatedAbility(
                () -> !depleted,
                () -> {
                    int x = game.selectX(controller, game.getEnergy(controller));
                    UUID target = game.selectDamageTargets(controller, 1, false).get(0);
                    game.spendEnergy(controller, x);
                    game.deplete(id);
                    game.addToStack(new AbilityCard(game, this,
                            "Deal " + x + " damage",
                            () -> {
                                game.dealDamage(id, target, x);
                            }, target));
                }
        ));
    }
}
