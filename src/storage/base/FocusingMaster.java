/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;


import com.visitor.card.types.Ally;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class FocusingMaster extends Ally {

    public FocusingMaster(Game game, UUID owner) {
        super(game, "Focusing Master", 2, new CounterMap<>(GREEN, 1),
                "-1 Max Energy, {Use}: \n" +
                        "    +2 Loyalty\n" +
                        "-1 Loyalty, {Use}:\n" +
                        "    Delay 1 - Deal X damage, X = your energy.",
                3,
                owner);

        addPlusLoyaltyAbility(0, "", 2, null, () -> game.hasMaxEnergy(controller, 1), () -> game.removeMaxEnergy(controller, 1));

        addMinusLoyaltyAbility(0, "", 1, 1,
                new ActivatedAbility(game, this, 0, "")
                        .setTargetingForDamage(targetID -> game.dealDamage(id, targetID, game.getPlayerEnergy(controller))), null, null);
    }
}

