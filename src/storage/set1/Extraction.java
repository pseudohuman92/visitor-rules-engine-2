/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Extraction extends Spell {

    UUID target;

    public Extraction(String owner) {
        super("Extraction", 1, new Hashmap(RED, 2),
                "Additional Cost \n" +
                        "  Return an asset you control to your hand. \n" +
                        "Deal 4 damage.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Play, Predicates::isAsset, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        target = game.selectFromZone(controller, Play, Predicates::isAsset, 1, false).get(0);
        game.getCard(target).returnToHand(game);
    }

    @Override
    protected void duringResolve(Game game) {
        target = game.selectDamageTargets(controller, 1, false).get(0);
        game.dealDamage(id, target, 4);
    }
}
