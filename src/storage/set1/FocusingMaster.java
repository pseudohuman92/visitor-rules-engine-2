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
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class FocusingMaster extends Ally {

    public FocusingMaster(String owner) {
        super("Focusing Master", 2, new Hashmap(GREEN, 1),
                "-1 Max Energy, Activate: \n" +
                        "    +2 Loyalty\n" +
                        "-1 Loyalty, Activate:\n" +
                        "    Delay 1 - Deal X damage, X = your energy.",
                3,
                owner);
    }


    @Override
    public boolean canActivateAdditional(Game game) {
        return (game.hasMaxEnergy(controller, 1) || loyalty >= 1);
    }

    @Override
    public void activate(Game game) {
        Arraylist<Card> choices = new Arraylist<>();
        if (game.hasMaxEnergy(controller, 1)) {
            choices.add(new AbilityCard(this,
                    "-1 Max Energy, Activate: \n" +
                            "    +2 Loyalty",
                    (x1) -> {
                        game.deplete(id);
                        game.removeMaxEnergy(controller, 1);
                        game.addToStack(new AbilityCard(this, "+2 Loyalty",
                                (x2) -> {
                                    loyalty += 2;
                                }));
                    }));
        }
        if (loyalty >= 1) {
            choices.add(new AbilityCard(this,
                    "-1 Loyalty, Activate:\n" +
                            "    Delay 1 - Deal X damage, X = your energy.",
                    (x1) -> {
                        loyalty -= 1;
                        game.deplete(id);
                        delayCounter = 1;
                        delayedAbility = new AbilityCard(this, "Deal X damage, X = your energy.",
                                (x2) -> {
                                    targets = game.selectDamageTargets(controller, 1, true);
                                    if (!targets.isEmpty()) {
                                        game.dealDamage(id, targets.get(0), game.getEnergy(controller));
                                    }
                                });
                    }));
        }
        Arraylist<UUID> selection = game.selectFromList(controller, choices, Predicates::any, 1, false);
        getInList(choices, selection).get(0).resolve(game);
    }

}

