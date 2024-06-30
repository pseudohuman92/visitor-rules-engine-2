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

import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class SaulgolathEnforcer extends Ally {

    public SaulgolathEnforcer(Game game, UUID owner) {
        super(game, "Sa'ulgolath Enforcer", 1, new CounterMap<>(RED, 1),
                "Pay 2 life, {Use}: +2 Loyalty\n" +
                        "-{X} Loyalty, {Use}: \n" +
                        "  Delay 1 - Deal 2X damage to opponent.", 5,
                owner);

        addPlusLoyaltyAbility(0, "Pay 2 life, Activate: +2 Loyalty", 2, null,
                () -> game.hasHealth(controller, 2),
                () -> game.payHealth(controller, 2));

        addMinusLoyaltyAbilityWithX(0, "-{X} Loyalty, {Use}: Delay 1 - Deal 2X damage to opponent.", 0, 1,
                x -> new ActivatedAbility(game, this, 0, "Deal 2X damage to opponent.", () -> game.dealDamage(id, game.getOpponentId(controller), 2 * x)),
                null, x -> loyalty -= x, loyalty);
    }

}

