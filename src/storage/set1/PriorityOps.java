/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.game.Game.Zone.Deck;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class PriorityOps extends Spell {

    UUID target;

    public PriorityOps(String owner) {
        super("Priority Ops", 2, new Hashmap(RED, 2),
                "Put target asset on top of controller's deck.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Both_Play, Predicates::isAsset, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Both_Play, Predicates::isAsset, 1, false);
        target = targets.get(0);


    }

    @Override
    protected void duringResolve(Game game) {
        if (game.isIn(controller, target, Both_Play)) {
            Card c = game.extractCard(target);
            c.clear();
            game.putTo(c.controller, c, Deck, 0);
        }
    }
}
