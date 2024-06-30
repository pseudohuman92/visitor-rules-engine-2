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
import com.visitor.protocol.Types;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class Unity extends Ritual {

    int x = -1;

    public Unity(String owner) {
        super("Unity", 2, new Hashmap(GREEN, 2),
                "Target ally gains X loyalty", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) &&
                game.hasEnergy(controller, 2) &&
                game.hasIn(controller, Both_Play, Predicates::isAlly, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        x = game.selectX(controller, game.getEnergy(controller) - 2);
        targets = game.selectFromZone(controller, Both_Play, Predicates::isAlly, 1, false);
        cost += x;
        text = "Target ally gains " + x + " loyalty";
    }

    @Override
    protected void duringResolve(Game game) {
        if (game.isIn(controller, targets.get(0), Both_Play)) {
            ((Ally) game.getCard(targets.get(0))).loyalty += x;
        }
        text = "Target ally gains X loyalty";
        cost = 2;
        x = -1;
    }

    @Override
    public Types.Card.Builder toCardMessage() {
        return super.toCardMessage()
                .setCost(x == -1 ? "X+2" : ("" + cost));
    }
}

