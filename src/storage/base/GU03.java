/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.Ability;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class GU03 extends Unit {

    public GU03(Game game, UUID owner) {
        super(game, "GU03",
                2, new CounterMap(BLUE, 1).add(GREEN, 1),
                "Whenever {~} deals damage to an opponent, draw a card.",
                1, 1,
                owner);

        combat.addDamageEffect((targetId, damage) -> {
            if (game.isPlayer(targetId) && !controller.equals(targetId)) {
                game.addToStack(new Ability(game, this, "Whenever {~} deals damage to an opponent, draw a card.",
                        () -> game.draw(controller, 1)));
            }
        });
    }
}
