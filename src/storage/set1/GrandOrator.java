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

import static com.visitor.game.Game.Zone.Play;
import static com.visitor.helpers.UUIDHelper.getInList;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class GrandOrator extends Ally {

    public GrandOrator(String owner) {
        super("Grand Orator", 2, new Hashmap(GREEN, 2),
                "2, Activate: +1 Loyalty.\n" +
                        "-1 Loyalty, Activate:\n" +
                        "      Delay 1 - All other allies you control gain 1 Loyalty", 2,
                owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return
                (game.hasEnergy(controller, 2) ||
                        loyalty >= 1);
    }


    @Override
    public void activate(Game game) {
        Arraylist<Card> choices = new Arraylist<>();
        if (game.hasEnergy(controller, 2)) {
            choices.add(new AbilityCard(this,
                    "2, Activate: +1 Loyalty.",
                    (x1) -> {
                        game.deplete(id);
                        game.spendEnergy(controller, 2);
                        game.addToStack(new AbilityCard(this, "+1 Loyalty",
                                (x2) -> {
                                    loyalty += 1;
                                }));
                    }));
        }
        if (loyalty >= 1) {
            choices.add(new AbilityCard(this,
                    "-1 Loyalty, Activate:\n" +
                            "      Delay 1 - All other allies you control gain 1 Loyalty",
                    (x1) -> {
                        game.deplete(id);
                        loyalty -= 1;
                        delayCounter = 1;
                        delayedAbility = new AbilityCard(this, "All other allies you control gain 1 Loyalty.",
                                (x2) -> {
                                    Arraylist<Card> allies = game.getAllFrom(controller, Play, c -> {
                                        return c instanceof Ally && !c.id.equals(id);
                                    });
                                    allies.forEach(c -> {
                                        ((Ally) c).loyalty++;
                                    });
                                });
                    }));
        }
        Arraylist<UUID> selection = game.selectFromList(controller, choices, Predicates::any, 1, false);
        getInList(choices, selection).get(0).resolve(game);
    }

}

