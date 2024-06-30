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
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class CrossroadTrade extends Spell {

    public CrossroadTrade(Game game, String owner) {
        super(game, "Crossroad Trade", 1, new Hashmap(BLACK, 1),
                "Additional Cost - Sacrifice an asset. Draw 2 cards.", owner);

        playable.setCanPlayAdditional(() -> game.hasIn(controller, Play, Predicates::isAsset, 1))
                .setBeforePlay(() -> {
                    UUID target = game.selectFromZone(controller, Play, Predicates::isAsset, 1, false).get(0);
                    game.sacrifice(target);
                })
                .setResolveEffect(() -> {
                    game.draw(controller, 2);
                });

    }
}
