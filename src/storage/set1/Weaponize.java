/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Ritual;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class Weaponize extends Ritual {

    UUID junkToDiscard;

    public Weaponize(String owner) {
        super("Weaponize", 2, new Hashmap(BLUE, 1),
                "Additional Cost - Discard a Junk\n" +
                        "    Deal 4 damage to your opponent.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Hand, Predicates::isJunk, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        junkToDiscard = game.selectFromZone(controller, Hand, Predicates::isJunk, 1, false).get(0);
        game.discard(controller, junkToDiscard);
    }

    @Override
    protected void duringResolve(Game game) {
        game.dealDamage(id, game.getOpponentId(controller), 4);
    }
}
