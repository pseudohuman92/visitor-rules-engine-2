/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;

import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class WalkingFire extends Spell {

    public WalkingFire(String owner) {
        super("Walking Fire", 2, new Hashmap(RED, 2),
                "Deal 2 damage \n" +
                        "Shuffle ~ to your deck.", owner);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectDamageTargets(controller, 1, false);
    }

    @Override
    protected void duringResolve(Game game) {
        game.dealDamage(id, targets.get(0), 2);
        game.shuffleIntoDeck(controller, new Arraylist<>(this));
    }
}
