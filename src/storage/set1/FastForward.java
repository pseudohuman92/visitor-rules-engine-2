/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class FastForward extends Spell {

    public FastForward(String owner) {
        super("Fast Forward", 3, new Hashmap(YELLOW, 2),
                "Each player draws 3 cards", owner);
    }

    @Override
    protected void duringResolve(Game game) {
        game.draw(controller, 3);
        game.draw(game.getOpponentName(controller), 3);
    }
}
