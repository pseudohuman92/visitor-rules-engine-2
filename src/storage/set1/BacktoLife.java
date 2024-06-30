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

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class BacktoLife extends Spell {

    public BacktoLife(Game game, String owner) {
        super(game, "Back To Life", 2, new Hashmap(GREEN, 1),
                "Return target Ally from your scrapyard to your hand.", owner);

        playable.setCanPlayAdditional(() -> game.hasIn(controller, Scrapyard, Predicates::isAlly, 1))
                .setBeforePlay(() -> {
                    targets = game.selectFromZone(controller, Scrapyard, Predicates::isAlly, 1, false);
                })
                .setResolveEffect(() -> {
                    if (game.isIn(controller, targets.get(0), Scrapyard)) {
                        Card c = game.extractCard(targets.get(0));
                        game.putTo(controller, c, Hand);
                    }
                });
    }
}

