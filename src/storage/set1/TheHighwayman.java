/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;


import com.visitor.card.types.Ally;
import com.visitor.card.types.Card;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.helpers.UUIDHelper.getInList;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class TheHighwayman extends Ally {

    public TheHighwayman(String owner) {
        super("The Highwayman", 2, new Hashmap(BLACK, 2),
                "3, Activate: +2 Loyalty\n" +
                        "-1 Loyalty, Activate: \n" +
                        "  Delay 2 - Draw the top asset of opponent's deck.", 5,
                owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return
                (game.hasEnergy(controller, 3) || loyalty >= 1);
    }


    @Override
    public void activate(Game game) {
        Arraylist<Card> choices = new Arraylist<>();
        if (game.hasEnergy(controller, 3)) {
            choices.add(new AbilityCard(this, "Pay 3: +2 Loyalty",
                    (x1) -> {
                        game.deplete(id);
                        game.spendEnergy(controller, 3);
                        game.addToStack(new AbilityCard(this, "+2 Loyalty",
                                (x2) -> {
                                    loyalty += 2;
                                }));
                    }));
        }
        if (loyalty >= 1) {
            choices.add(new AbilityCard(this, "-1 Loyalty: Delay 2:\n" +
                    "  Draw the top asset of opponent's deck.",
                    (x1) -> {
                        game.deplete(id);
                        loyalty -= 1;
                        delayCounter = 2;
                        delayedAbility = new AbilityCard(this, "Draw the top asset of opponent's deck.",
                                (x2) -> {
                                    Card c = game.getPlayer(game.getOpponentName(controller))
                                            .deck.extractTopmost(Predicates::isAsset);
                                    if (c != null) {
                                        c.controller = controller;
                                        c.knowledge = new Hashmap<>();
                                        game.putTo(c.controller, c, Hand);
                                    }
                                });
                    }));
        }
        Arraylist<UUID> selection = game.selectFromList(controller, choices, Predicates::any, 1, false);
        getInList(choices, selection).get(0).resolve(game);
    }

}

