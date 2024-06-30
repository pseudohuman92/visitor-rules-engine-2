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

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.helpers.UUIDHelper.getInList;
import static com.visitor.protocol.Types.Counter.CHARGE;
import static com.visitor.protocol.Types.Knowledge.RED;


/**
 * @author pseudo
 */
public class HarmfulResources extends Asset {

    public HarmfulResources(String owner) {
        super("Harmful Resources", 2, new Hashmap(RED, 3),
                "\"Discard a card: \n" +
                        "  Charge 1. \n" +
                        "Discharge 1, Activate: \n" +
                        "  Deal 2 damage", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasIn(controller, Hand, Predicates::any, 1)
                || (counters.getOrDefault(CHARGE, 0) > 0);
    }

    @Override
    public void activate(Game game) {
        Arraylist<Card> choices = new Arraylist<>();
        if (game.hasIn(controller, Hand, Predicates::any, 1)) {
            choices.add(new AbilityCard(this, "Discard a card: Charge 1.",
                    (x1) -> {
                        game.discard(controller, 1);
                        game.addToStack(new AbilityCard(this, "Charge 1",
                                (x2) -> {
                                    addCounters(CHARGE, 1);
                                }));
                    }));
        }
        if (counters.getOrDefault(CHARGE, 0) > 0) {
            choices.add(new AbilityCard(this, "Discharge 1, Activate: Deal 2 damage",
                    (x1) -> {
                        removeCounters(CHARGE, 1);
                        game.deplete(id);
                        UUID target = game.selectDamageTargets(controller, 1, false).get(0);
                        game.addToStack(new AbilityCard(this,
                                "Deal 2 damage",
                                (x) -> {
                                    game.dealDamage(id, target, 2);
                                })
                        );
                    }));
        }
        Arraylist<UUID> selection = game.selectFromList(controller, choices, Predicates::any, 1, false);
        getInList(choices, selection).get(0).resolve(game);
    }


}
