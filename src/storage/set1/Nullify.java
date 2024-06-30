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

import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.game.Game.Zone.Stack;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class Nullify extends Spell {

    UUID target;

    public Nullify(String owner) {
        super("Nullify", 2, new Hashmap(YELLOW, 1),
                "Cancel target spell.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Stack, Predicates::isSpell, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Stack, Predicates::isSpell, 1, false);
        target = targets.get(0);


    }

    @Override
    protected void duringResolve(Game game) {
        Card c = game.extractCard(target);
        game.putTo(c.controller, c, Scrapyard);
    }
}
