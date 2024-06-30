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
public class TheBoss extends Ally {

    public TheBoss(String owner) {
        super("The Boss", 2, new Hashmap(BLACK, 1),
                "Discard a card, Activate:    \n" +
                        "    +1 Loyalty. Opponent discards a card.\n" +
                        "Condition - Opponent has no cards in hand\n" +
                        "  -2 Loyalty, Activate: \n" +
                        "    Delay 1 - Deal 2 damage",
                1,
                owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return
                (!game.getZone(controller, Hand).isEmpty() ||
                        (loyalty >= 2 && game.getZone(game.getOpponentName(controller), Hand).isEmpty()));
    }


    @Override
    public void activate(Game game) {
        Arraylist<Card> choices = new Arraylist<>();
        if (!game.getZone(controller, Hand).isEmpty()) {
            choices.add(new AbilityCard(this,
                    "Discard a card, Activate:    \n" +
                            "    +1 Loyalty. Opponent discards a card.",
                    (x1) -> {
                        game.discard(controller, 1);
                        game.deplete(id);
                        game.addToStack(new AbilityCard(this, "+2 Loyalty",
                                (x2) -> {
                                    loyalty += 1;
                                    game.discard(game.getOpponentName(controller), 1);
                                }));
                    }));
        }
        if (loyalty >= 2 && game.getZone(game.getOpponentName(controller), Hand).isEmpty()) {
            choices.add(new AbilityCard(this,
                    "-2 Loyalty, Activate: \n" +
                            "    Delay 1 - Deal 2 damage",
                    (x1) -> {
                        UUID target = game.selectDamageTargets(controller, 1, false).get(0);
                        loyalty -= 2;
                        game.deplete(id);
                        delayCounter = 1;
                        delayedAbility = new AbilityCard(this,
                                "Deal 2 damage",
                                (x2) -> {
                                    game.dealDamage(id, target, 2);

                                }, target);
                    }));
        }
        Arraylist<UUID> selection = game.selectFromList(controller, choices, Predicates::any, 1, false);
        getInList(choices, selection).get(0).resolve(game);
    }

}

