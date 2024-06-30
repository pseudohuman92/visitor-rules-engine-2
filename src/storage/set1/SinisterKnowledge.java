/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Event;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Event.EventType.STUDY;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class SinisterKnowledge extends TriggeringPassive {

    public SinisterKnowledge(String owner) {
        super("Sinister Knowledge", 1, new Hashmap(GREEN, 2),
                "Trigger - When you study\n" +
                        "    Deal 1 damage to your opponent", owner);
    }

    @Override
    public void checkEvent(Game game, Event event) {
        if (event.type == STUDY
                && ((String) event.data.get(0)).equals(controller)) {
            game.addToStack(new AbilityCard(this,
                    "Deal 1 damage to your opponent.",
                    (x) -> {
                        game.dealDamage(id, game.getOpponentId(controller), 1);
                    }));
        }
    }
}
