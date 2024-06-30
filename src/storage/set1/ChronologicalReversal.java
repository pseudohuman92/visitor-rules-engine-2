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

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class ChronologicalReversal extends Spell {

    UUID selected;

    public ChronologicalReversal(Game game, String owner) {
        super(game, "Chronological Reversal", 1, new Hashmap(YELLOW, 1),
                "Return target spell from scrapyard to your hand.\n" +
                        "Purge ~", owner);
        playable.setPurging()
                .setCanPlayAdditional(() -> game.hasIn(controller, Scrapyard, Predicates::isSpell, 1))
                .setBeforePlay(() -> {
                    targets = game.selectFromZone(controller, Scrapyard, Predicates::isSpell, 1, false);
                    selected = targets.get(0);
                })
                .setResolveEffect(() -> {
                    if (game.isIn(controller, selected, Scrapyard)) {
                        game.putTo(controller, game.extractCard(selected), Hand);
                    }
                });
    }
}
