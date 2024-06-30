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

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class SpontaneousExplosion extends Spell {

    public SpontaneousExplosion(String owner) {
        super("Spontaneous Explosion", 1, new Hashmap(BLUE, 2),
                "Destroy target Junk.\n" +
                        "Its controller takes 3 damage.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Both_Play, Predicates::isJunk, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Both_Play, Predicates::isJunk, 1, false);
    }

    @Override
    protected void duringResolve(Game game) {
        if (game.isIn(controller, targets.get(0), Both_Play)) {
            Card c = game.getCard(targets.get(0));
            game.destroy(id, targets.get(0));
            game.dealDamage(id, game.getUserId(c.controller), 3);
        }
    }
}
