/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Ally;
import com.visitor.card.types.Card;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Event;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Event.EventType.DESTROY;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class Rebuke extends TriggeringPassive {

    public Rebuke(String owner) {
        super("Rebuke", 2, new Hashmap(GREEN, 1),
                "Trigger -- an ally you control is destroyed.\n" +
                        "    Deal 3 damage to your opponent", owner);
    }

    @Override
    public void checkEvent(Game game, Event event) {
        if (event.type == DESTROY) {
            Card c = ((Card) event.data.get(1));
            if (c instanceof Ally && c.controller.equals(controller)) {
                game.addToStack(new AbilityCard(this,
                        "Deal 3 damage to your opponent.",
                        (x) -> {
                            game.dealDamage(id, game.getOpponentId(controller), 3);
                        }));
            }
        }
    }
}
