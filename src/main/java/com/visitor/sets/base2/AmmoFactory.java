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

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class AmmoFactory extends Asset {

    public AmmoFactory(Game game, UUID owner) {
        super(game, "Ammo Factory",
                5, new CounterMap(RED, 3),
                "At the start of each turn, create and draw an Explosive Shot.",
                owner);
        triggering.addEventChecker(EventChecker.startOfTurnChecker(game, this,
                () -> game.draw(controller, new ExplosiveShot(game, controller))));
    }
}
