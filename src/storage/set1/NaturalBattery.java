/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.properties.Triggering;
import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Event;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Event.playersTurnStart;
import static com.visitor.protocol.Types.Knowledge.GREEN;


/**
 * @author pseudo
 */
public class NaturalBattery extends Asset implements Triggering {

    public NaturalBattery(String owner) {
        super("Natural Battery", 3, new Hashmap(GREEN, 1),
                "Trigger - Beginning of your turn\n" +
                        "    Gain 1 energy\n" +
                        "X, Activate, Sacrifice ~:\n" +
                        "   Deal X damage to each player and each card in play.", 1, owner);
    }

    @Override
    public void activate(Game game) {
        int x = game.selectX(controller, game.getEnergy(controller));
        game.deplete(id);
        game.sacrifice(id);
        game.addToStack(new AbilityCard(this,
                "Deal " + x + " damage to each player and each card in play.",
                a -> {
                    game.dealDamageToAll(controller, id, x);
                })
        );
    }

    @Override
    public void checkEvent(Game game, Event event) {
        if (playersTurnStart(event, controller)) {
            game.addToStack(new AbilityCard(this,
                    "Gain 1 energy",
                    a -> {
                        game.addEnergy(controller, 1);
                    })
            );
        }
    }
}
