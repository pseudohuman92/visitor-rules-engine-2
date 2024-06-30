/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class BlackBear extends Unit {

    public BlackBear(Game game, UUID owner) {
        super(game, "Black Bear",
                2, new CounterMap(GREEN, 1),
                "{2}, Return {~} to your hand: Draw a card.",
                3, 3,
                owner);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 2, "Draw a card.",
                        () -> game.returnToHand(id),
                        () -> game.draw(controller, 1)));
    }
}
