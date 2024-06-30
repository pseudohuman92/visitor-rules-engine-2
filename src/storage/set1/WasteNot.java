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

import static com.visitor.game.Event.EventType.DISCARD;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class WasteNot extends TriggeringPassive {

    public WasteNot(String owner) {
        super("Waste Not", 1, new Hashmap(BLACK, 2),
                "Trigger - When opponent discards a card\n" +
                        "  Deal 2 damage to opponent.", owner);
    }


    @Override
    public void checkEvent(Game game, Event event) {
        if (event.type.equals(DISCARD)
                && event.data.get(0).equals(game.getOpponentName(controller))) {

            String discardingPlayer = ((String) event.data.get(0));
            game.addToStack(new AbilityCard(this,
                    "Deal 2 damage to opponent",
                    (x) -> {
                        game.dealDamage(id, game.getUserId(discardingPlayer), 2);
                    }, new Arraylist<>(game.getUserId(discardingPlayer))));
        }
    }
}
