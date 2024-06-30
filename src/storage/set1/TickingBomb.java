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
import com.visitor.protocol.Types.Counter;

import static com.visitor.game.Event.playersTurnStart;
import static com.visitor.protocol.Types.Knowledge.BLACK;


/**
 * @author pseudo
 */
public class TickingBomb extends Asset implements Triggering {

    public TickingBomb(String owner) {
        super("Ticking Bomb", 2, new Hashmap(BLACK, 2),
                "Donate, Charge 2\n" +
                        "Trigger - At the beginning of your turn\n" +
                        "    Dischage 1. If ~ has no counters on it, sacricife it and take 5 damage.\n" +
                        "1, Activate: Charge 1", 1, owner);
        addCounters(Counter.CHARGE, 2);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasEnergy(controller, 1);
    }

    @Override
    public void activate(Game game) {
        game.spendEnergy(controller, 1);
        game.deplete(id);
        game.addToStack(new AbilityCard(this,
                "Charge 1",
                a -> {
                    addCounters(Counter.CHARGE, 1);
                })
        );
    }

    @Override
    public void checkEvent(Game game, Event event) {
        if (playersTurnStart(event, controller)) {
            game.addToStack(new AbilityCard(this,
                    "Dischage 1. If ~ has no counters on it, sacricife it and take 5 damage.",
                    a -> {
                        if (!removeCounters(Counter.CHARGE, 1)) {
                            game.sacrifice(id);
                            game.dealDamage(id, game.getUserId(controller), 5);
                        }
                    })
            );
        }
    }
}