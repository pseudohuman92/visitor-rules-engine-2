/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.properties.Triggering;
import com.visitor.card.types.Spell;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.card.types.helpers.TemporaryEffect;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Event.playersTurnStart;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class Regentersize extends Spell {

    public Regentersize(String owner) {
        super("Regentersize", 2, new Hashmap(GREEN, 1),
                "Gain 2 health and 1 energy at the start of your next turn.", owner);
    }

    @Override
    protected void duringResolve(Game game) {
        new TemporaryEffect(game, this,
                (t, e) -> {
                    if (playersTurnStart(e, controller)) {
                        game.addToStack(new AbilityCard(this, "Gain 2 health and 1 energy.",
                                a -> {
                                    game.gainHealth(controller, 2);
                                    game.addEnergy(controller, 1);
                                }));
                        game.removeTriggeringCard((Triggering) t);
                    }
                });
    }

}
