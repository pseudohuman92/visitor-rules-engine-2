/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class CreepingPoison extends Asset {

    public CreepingPoison(Game game, UUID owner) {
        super(game, "Creeping Poison", 3, new CounterMap<>(PURPLE, 2),
                "Donate\n" +
                        "Trigger - At the start of your turn\n" +
                        "    Deal 1 damage to your controller.", owner);

        setDonating();

        triggering.addEventChecker(new EventChecker(game, this,
                event -> game.dealDamage(id, controller, 1))
                .addStartOfControllerTurnChecker()
                .createAbility("Deal 1 damage to your controller."));
    }
}
