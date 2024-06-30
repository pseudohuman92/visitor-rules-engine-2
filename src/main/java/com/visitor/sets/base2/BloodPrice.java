/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class BloodPrice extends Asset {

    public BloodPrice(Game game, UUID owner) {
        super(game, "Blood Price",
                2, new CounterMap(PURPLE, 1),
                "At the start of your turn, draw a card and lose life equal to its cost.",
                owner);
        triggering.addEventChecker(EventChecker.startOfTurnChecker(game, this, true, () -> {
            Arraylist<UUID> drawn = game.draw(controller, 1);
            game.loseHealth(controller, game.getCard(drawn.get(0)).getCost());
        }));
    }
}
