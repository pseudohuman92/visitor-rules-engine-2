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
public class MysticGnome extends Ally {

    public MysticGnome(String owner) {
        super("Mystic Gnome", 1, new Hashmap(GREEN, 1),
                "1, Activate: +1 Loyalty \n" +
                        "-2 Loyalty, Activate: \n" +
                        "    Delay 1 - Deal X damage to a target. \n" +
                        "    X = Your max energy.",
                2,
                owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return
                (game.hasEnergy(controller, 1) ||
                        loyalty >= 2);
    }


    @Override
    public void activate(Game game) {
        Arraylist<Card> choices = new Arraylist<>();
        if (game.hasEnergy(controller, 1)) {
            choices.add(new AbilityCard(this,
                    "1, Activate: +1 Loyalty",
                    (x1) -> {
                        game.deplete(id);
                        game.spendEnergy(controller, 2);
                        game.addToStack(new AbilityCard(this, "+1 Loyalty",
                                (x2) -> {
                                    loyalty += 1;
                                }));
                    }));
        }
        if (loyalty >= 2) {
            choices.add(new AbilityCard(this,
                    "-2 Loyalty, Activate: \n" +
                            "    Delay 1 - Deal X damage to a target. \n" +
                            "    X = Your max energy.",
                    (x1) -> {
                        targets = game.selectDamageTargets(controller, 1, false);
                        game.deplete(id);
                        loyalty -= 2;
                        delayCounter = 1;
                        delayedAbility = new AbilityCard(this,
                                "Deal X damage to a target. \n" +
                                        "    X = Your max energy.",
                                (x2) -> {
                                    game.dealDamage(id, targets.get(0), game.getMaxEnergy(controller));
                                }, targets);
                    }));
        }
        Arraylist<UUID> selection = game.selectFromList(controller, choices, Predicates::any, 1, false);
        getInList(choices, selection).get(0).resolve(game);
    }

}

