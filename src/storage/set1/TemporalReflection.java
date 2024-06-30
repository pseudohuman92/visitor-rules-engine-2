/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class TemporalReflection extends Spell {

    UUID target;

    public TemporalReflection(String owner) {
        super("Temporal Reflection", 2, new Hashmap(YELLOW, 3),
                "Gain reflect 2.\n" +
                        "Shuffle ~ back into your deck.", owner);
    }

    @Override
    protected void duringResolve(Game game) {
        game.addReflect(controller, 2);
        Arraylist<Card> tmp = new Arraylist<>();
        tmp.add(this);
        game.shuffleIntoDeck(controller, tmp);
    }
}
