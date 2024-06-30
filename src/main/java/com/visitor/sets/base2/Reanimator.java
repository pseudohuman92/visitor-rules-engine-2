/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class Reanimator extends Asset {

    public Reanimator(Game game, UUID owner) {
        super(game, "Reanimator",
                2, new CounterMap(PURPLE, 2),
                "Whenever a unit you control dies, create a Bat and lose 1 life.",
                owner);
        triggering.addEventChecker(EventChecker.deathChecker(game, this, c -> {
            if (Predicates.isUnit(c) && c.controller.equals(controller)){
                UnitToken.Bat_1_1(game, controller).resolve();
                game.loseHealth(controller, 1);
            }
        }));
    }
}
