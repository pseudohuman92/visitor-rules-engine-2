/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.Card;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.game.Game.Zone.Void;
import static com.visitor.protocol.Types.Knowledge.RED;
import static java.lang.Math.min;


/**
 * @author pseudo
 */
public class ShockTroop extends Asset {

    public ShockTroop(String owner) {
        super("Shock Troop", 1, new Hashmap(RED, 2),
                "X, Purge X, Activate: \n" +
                        "  Opponent purge X", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return
                game.hasEnergy(controller, 1) &&
                        game.hasIn(controller, Scrapyard, Predicates::any, 1);
    }

    @Override
    public void activate(Game game) {
        int x = game.selectX(controller, min(game.getEnergy(controller), game.getZone(controller, Scrapyard).size()));
        Arraylist<UUID> selection = game.selectFromZone(controller, Scrapyard, Predicates::any, x, false);
        UUID target = game.selectDamageTargets(controller, 1, false).get(0);
        game.spendEnergy(controller, x);
        Arraylist<Card> cards = game.extractAll(selection);
        game.putTo(controller, cards, Void);
        game.deplete(id);
        game.addToStack(new AbilityCard(this, "Deal " + x + " damage",
                (y) -> {
                    game.dealDamage(id, target, x);
                }, new Arraylist(selection).putIn(target)));
    }
}
