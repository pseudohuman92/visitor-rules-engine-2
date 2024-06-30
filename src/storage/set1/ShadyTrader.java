/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;


import com.visitor.card.types.Ally;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class ShadyTrader extends Ally {

    public ShadyTrader(String owner) {
        super("Shady Trader", 1, new Hashmap(BLUE, 1),
                "Discard an Asset, Activate: +3 Loyalty\n" +
                        "If ~ has 6 or more loyalty, Transform it to Recoverer", 5,
                owner);
    }

    public ShadyTrader(Recoverer c) {
        super("Shady Trader", 1, new Hashmap(BLUE, 1),
                "Discard an Asset, Activate: +3 Loyalty\n" +
                        "If ~ has 6 or more loyalty, Transform it to Recoverer", 5,
                c.controller);
        copyPropertiesFrom(c);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasIn(controller, Hand, Predicates::isAsset, 1);
    }


    @Override
    public void activate(Game game) {
        UUID target = game.selectFromZone(controller, Hand, Predicates::isAsset, 1, false).get(0);
        game.discard(controller, target);
        game.deplete(id);
        loyalty += 3;
        if (loyalty >= 6) {
            game.transformTo(this, this, new Recoverer(this));
        }
    }

}

