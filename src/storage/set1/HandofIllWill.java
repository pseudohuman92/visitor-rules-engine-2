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
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class HandofIllWill extends Asset {

    UUID target;

    public HandofIllWill(String owner) {
        super("Hand of Ill Will", 4, new Hashmap(BLACK, 3),
                "Sacrifice an asset, Activate:\n" +
                        "Deal 2 damage to your opponent.\n" +
                        "If sacrificed asset belongs to him, deal 3 damage instead.", owner);
        subtypes.add("Weapon");
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasIn(controller, Play, Predicates::isAsset, 1);
    }

    @Override
    public void activate(Game game) {
        target = game.selectFromZone(controller, Play, Predicates::isAsset, 1, false).get(0);
        game.deplete(id);
        game.sacrifice(target);
        game.addToStack(new AbilityCard(this, "deal " + (game.ownedByOpponent(target) ? 3 : 2) +
                " damage to " + game.getOpponentName(controller),
                (x) -> {
                    game.dealDamage(id, target, (game.ownedByOpponent(target) ? 3 : 2));
                }));
    }
}
