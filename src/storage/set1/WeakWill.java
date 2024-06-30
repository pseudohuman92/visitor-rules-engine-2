/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class WeakWill extends Spell {

    UUID target;

    public WeakWill(String owner) {
        super("Weak Will", 3, new Hashmap(BLACK, 2), "Possess target asset that costs 3 or less.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) &&
                game.hasIn(controller, Both_Play, c -> {
                    return (c instanceof Asset && c.cost <= 3);
                }, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Both_Play, c -> {
            return (c instanceof Asset && c.cost <= 3);
        }, 1, false);
        target = targets.get(0);


    }

    @Override
    protected void duringResolve(Game game) {
        if (game.isIn(controller, target, Both_Play)) {
            game.possessTo(controller, target, Play);
        }
    }
}
