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

import static com.visitor.helpers.UUIDHelper.getInList;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class SaulgolathEnforcer extends Ally {

    public SaulgolathEnforcer(String owner) {
        super("Sa'ulgolath Enforcer", 1, new Hashmap(RED, 1),
                "Pay 2 life, Activate: +2 Loyalty\n" +
                        "-X Loyalty, Activate: \n" +
                        "  Delay 1 - Deal 2X damage to opponent.", 5,
                owner);
    }

    @Override
    public void activate(Game game) {
        Arraylist<Card> choices = new Arraylist<>();
        choices.add(new AbilityCard(this, "Pay 2 life, Activate: +2 Loyalty",
                (x1) -> {
                    game.deplete(id);
                    game.payLife(controller, 2);
                    game.addToStack(new AbilityCard(this, "+2 Loyalty",
                            (x2) -> {
                                loyalty += 2;
                            }));
                }));
        if (loyalty >= 1) {
            choices.add(new AbilityCard(this,
                    "-X Loyalty, Activate: \n" +
                            "  Delay 1 - Deal 2X damage to opponent.",
                    (x1) -> {
                        int x = game.selectX(controller, loyalty);
                        game.deplete(id);
                        loyalty -= x;
                        delayCounter = 1;
                        delayedAbility = new AbilityCard(this, "Deal " + 2 * x + " damage to opponent.",
                                (x2) -> {
                                    game.dealDamage(id, game.getOpponentId(controller), 2 * x);
                                },
                                new Arraylist<>(game.getOpponentId(controller)));
                    }));
        }
        Arraylist<UUID> selection = game.selectFromList(controller, choices, Predicates::any, 1, false);
        getInList(choices, selection).get(0).resolve(game);
    }

}

