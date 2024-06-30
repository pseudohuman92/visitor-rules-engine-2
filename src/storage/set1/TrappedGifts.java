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

import static com.visitor.game.Event.EventType.DONATE;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class TrappedGifts extends TriggeringPassive {

    public TrappedGifts(String owner) {
        super("Trapped Gifts", 2, new Hashmap(BLACK, 1),
                "Trigger - When a card is donated\n" +
                        "    Donated card's new controller takes 1 damage.", owner);
    }

    @Override
    public void checkEvent(Game game, Event event) {
        if (event.type.equals(DONATE)) {
            String newOwner = ((String) event.data.get(1));

            game.addToStack(new AbilityCard(this,
                    "Deal 1 damage.",
                    (x) -> {
                        game.dealDamage(id, game.getUserId(newOwner), 1);
                    }, game.getUserId(newOwner)));
        }
    }
}
