/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;


import com.visitor.card.types.Ally;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.card.types.helpers.ActivatedAbility;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Event.EventType.STUDY;
import static com.visitor.game.Game.Zone.Deck;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class CuriousScholar extends Ally {

    public CuriousScholar(Game game, String owner) {
        super(game, "Curious Scholar", 2, new Hashmap(GREEN, 1),
                "Trigger - When you study\n" +
                        "    +1 Loyalty\n" +
                        "-2 Loyalty, Activate: \n" +
                        "    Delay 1 - Search your deck for a Green card and draw it",
                3,
                owner);
        triggering.addEventChecker((event) -> {
            if (event.type == STUDY && event.data.get(0).equals(controller)) {
                game.addToStack(new AbilityCard(game, this, "Add 1 Loyalty",
                        () -> {
                            loyalty += 1;
                        }));
            }
        });
        activatable.addActivatedAbility(new ActivatedAbility((() -> !depleted && loyalty >= 2),
                () -> {
                    loyalty -= 2;
                    game.deplete(id);
                    delayCounter = 1;
                    delayedAbility = new AbilityCard(game, this, "Search your deck for a Green card and draw it",
                            () -> {
                                UUID target = game.selectFromList(controller, game.getZone(controller, Deck), Predicates::isGreen, 1, false).get(0);
                                game.draw(controller, target);
                            });
                }));
    }

}

