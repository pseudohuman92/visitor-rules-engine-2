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
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class IdleHand extends Asset {

    public IdleHand(String owner) {
        super("Idle Hand", 3, new Hashmap(BLACK, 2),
                "3, Activate: Draw top asset of opponent's deck.", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return !depleted
                && game.hasEnergy(controller, 3);
    }

    @Override
    public void activate(Game game) {
        game.deplete(id);
        game.spendEnergy(controller, 3);
        game.addToStack(new AbilityCard(this,
                "Draw top asset of " + game.getOpponentName(controller) + "'s deck",
                (x) -> {
                    Card c = game.getPlayer(game.getOpponentName(controller))
                            .deck.extractTopmost(Predicates::isAsset);
                    if (c != null) {
                        c.controller = controller;
                        game.putTo(c.controller, c, Hand);
                    }
                }));
    }
}
