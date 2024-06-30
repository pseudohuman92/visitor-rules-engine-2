/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Ritual;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class SpotWeaknesses extends Ritual {


    public SpotWeaknesses(String owner) {
        super("Spot Weaknesses", 2, new Hashmap(BLACK, 1),
                "Deal 2X damage to target player.\n" +
                        "X = # of cards you control targeted player owns.", owner);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectPlayers(controller, Predicates::any, 1, false);
    }

    @Override
    protected void duringResolve(Game game) {
        Arraylist<Integer> counter = new Arraylist<>();
        counter.add(0, 0);
        String targetName = game.getUsername(targets.get(0));
        game.getZone(controller, Play).forEach(c -> {
            if (c.owner.equals(targetName)) {
                counter.add(0, counter.remove(0) + 1);
            }
        });
        game.dealDamage(id, targets.get(0), 2 * counter.get(0));
    }
}

