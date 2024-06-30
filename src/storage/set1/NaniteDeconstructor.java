/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.Card;
import com.visitor.card.types.Junk;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;


/**
 * @author pseudo
 */
public class NaniteDeconstructor extends Asset {

    public NaniteDeconstructor(String owner) {
        super("Nanite Deconstructor", 2, new Hashmap(BLUE, 1),
                "Shuffle 3 Junk into your deck, Activate: Deal 3 damage.", owner);
        subtypes.add("Kit");
    }

    @Override
    public void activate(Game game) {
        Arraylist<Card> junks = new Arraylist<>();
        junks.add(new Junk(controller));
        junks.add(new Junk(controller));
        junks.add(new Junk(controller));
        game.shuffleIntoDeck(controller, junks);
        game.deplete(id);
        UUID target = game.selectDamageTargets(controller, 1, false).get(0);
        game.addToStack(new AbilityCard(this,
                "Deal 3 damage",
                (y) -> {
                    game.dealDamage(id, target, 3);
                }, new Arraylist<>(target))
        );
    }
}
