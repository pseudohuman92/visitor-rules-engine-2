/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class HeatWave extends Asset {

    UUID target;

    public HeatWave(String owner) {
        super("Heat Wave", 1, new Hashmap(RED, 2),
                "Activate: \n" +
                        "  Return ~ and target asset to controller's hand.", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return
                game.hasIn(controller, Both_Play, c -> {
                    return (c instanceof Asset && !c.id.equals(id));
                }, 1);
    }

    @Override
    public void activate(Game game) {
        game.deplete(id);
        target = game.selectFromZone(controller, Both_Play, c -> {
            return (c instanceof Asset && !c.id.equals(id));
        }, 1, false).get(0);
        game.addToStack(new AbilityCard(this,
                "Return ~ and target asset to controller's hand.",
                (x) -> {
                    if (game.isIn(controller, target, Both_Play)) {
                        game.getCard(target).returnToHand(game);
                    }
                    if (game.isIn(controller, id, Both_Play)) {
                        returnToHand(game);
                    }
                }, new Arraylist<>(target))
        );
    }
}
