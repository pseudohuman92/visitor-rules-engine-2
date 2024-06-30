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
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.protocol.Types.Counter.CHARGE;
import static com.visitor.protocol.Types.Knowledge.YELLOW;


/**
 * @author pseudo
 */
public class NSink extends Asset {

    public NSink(String owner) {
        super("N-Sink", 2, new Hashmap(YELLOW, 2),
                "2X, Activate: \n" +
                        "  Target asset Charge X.", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return !depleted;
    }

    @Override
    public void activate(Game game) {
        int x = game.selectX(controller, game.getPlayer(controller).energy / 2);
        UUID selection = game.selectFromZone(controller, Both_Play, Predicates::isAsset, 1, false).get(0);
        game.spendEnergy(controller, 2 * x);
        game.deplete(id);
        game.addToStack(new AbilityCard(this, "Charge " + x,
                (y) -> {
                    if (game.isIn(controller, selection, Both_Play)) {
                        game.getCard(selection).addCounters(CHARGE, x);
                    }
                }, new Arraylist<>(selection)));
    }
}
