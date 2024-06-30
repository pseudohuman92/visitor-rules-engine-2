/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Event;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Event.EventType.POSSESS;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class Mugging extends TriggeringPassive {

    public Mugging(String owner) {
        super("Mugging", 2, new Hashmap(BLACK, 1),
                "Trigger - When you possess a card \n" +
                        "  Deal 2 damage to possessed card's controller", owner);
    }


    @Override
    public void checkEvent(Game game, Event event) {
        if (event.type.equals(POSSESS)
                && ((String) event.data.get(1)).equals(controller)) {
            String oldOwner = ((String) event.data.get(0));

            game.addToStack(new AbilityCard(this,
                    "Deal 2 damage to possessed card's controller",
                    (x) -> {
                        game.dealDamage(id, game.getUserId(oldOwner), 2);
                    }, new Arraylist<>(game.getUserId(oldOwner))));
        }
    }
}
