/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Ally;
import com.visitor.card.types.Ritual;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class FramedBetrayal extends Ritual {


    public FramedBetrayal(String owner) {
        super("Framed Betrayal", 4, new Hashmap(BLACK, 2),
                "Target Ally's loyalty becomes 0", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) &&
                game.hasIn(controller, Both_Play, Predicates::isAlly, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Both_Play, Predicates::isAlly, 1, false);
    }

    @Override
    protected void duringResolve(Game game) {
        if (game.isIn(controller, targets.get(0), Both_Play)) {
            ((Ally) game.getCard(targets.get(0))).loyalty = 0;
        }
    }
}

