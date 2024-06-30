/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Event;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Event.EventType.TRANSFORM;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class VolatileMaterials extends TriggeringPassive {

    public VolatileMaterials(String owner) {
        super("Volatile Materials", 1, new Hashmap(BLUE, 1),
                "Trigger - When a card is transformed. \n" +
                        "    That card's controller takes 2 damage", owner);
    }

    @Override
    public void checkEvent(Game game, Event event) {
        if (event.type == TRANSFORM) {
            game.addToStack(new AbilityCard(this,
                    "Transformed card's controller takes 2 damage",
                    (x) -> {
                        game.dealDamage(id, game.getUserId(((Card) event.data.get(1)).controller), 2);
                    }, ((Card) event.data.get(1)).id));
        }
    }
}
