/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class Necropainter extends Unit {

    public Necropainter(Game game, UUID owner) {
        super(game, "Necropainter",
                1, new CounterMap(PURPLE, 1),
                "Whenever a unit dies, your opponent loses 1 health and you gain 1 health.",
                1, 1,
                owner);
        triggering.addEventChecker(EventChecker.deathChecker(game, this, c ->
        {
            if (Predicates.isUnit(c)) {
                game.loseHealth(game.getOpponentId(controller), 1);
                game.gainHealth(controller, 1);
            }
        }));
    }
}
