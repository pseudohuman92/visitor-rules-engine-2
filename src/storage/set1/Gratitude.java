/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Ally;
import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class Gratitude extends Spell {

    public Gratitude(String owner) {
        super("Gratitude", 1, new Hashmap(GREEN, 1),
                "Target Ally you control gains 2 Loyalty", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) &&
                game.hasIn(controller, Play, Predicates::isAlly, 1);
    }

    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Play, Predicates::isAlly, 1, false);
    }

    @Override
    protected void duringResolve(Game game) {
        if (game.isIn(controller, targets.get(0), Play)) {
            ((Ally) game.getCard(targets.get(0))).loyalty += 2;
        }
    }
}

