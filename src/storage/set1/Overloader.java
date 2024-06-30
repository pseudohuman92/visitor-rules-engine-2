/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Counter.CHARGE;
import static com.visitor.protocol.Types.Knowledge.YELLOW;


/**
 * @author pseudo
 */
public class Overloader extends Asset {

    public Overloader(String owner) {
        super("Overloader", 2, new Hashmap(YELLOW, 2),
                "\"Sacrifice an asset, Activate:\n" +
                        "  Opponent purges X, \n" +
                        "  where X is # of charge counter on sacrificed asset.\"", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasIn(controller, Play, Predicates::isAsset, 1);
    }

    @Override
    public void activate(Game game) {
        UUID selection = game.selectFromZone(controller, Play, Predicates::isAsset, 1, false).get(0);
        int x = game.getCard(selection).counters.getOrDefault(CHARGE, 0);
        game.sacrifice(selection);
        game.deplete(id);
        game.addToStack(new AbilityCard(this, game.getOpponentName(controller) + " purges " + x,
                (y) -> {
                    game.dealDamage(id, game.getOpponentId(controller), x);
                }));
    }
}
