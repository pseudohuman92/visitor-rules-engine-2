/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class Discombobulate extends Spell {

    public Discombobulate(Game game, String owner) {
        super(game, "Discombobulate", 3, new Hashmap(BLUE, 1),
                "Discard all junk from your hand then deal 2 damage for each discarded junk to a target.", owner);

        playable.setBeforePlay(() -> {
            targets = game.selectDamageTargets(controller, 1, false);
        })
                .setResolveEffect(() -> {
                    Arraylist<Card> junks = game.getAllFrom(controller, Hand, Predicates::isJunk);
                    game.discardAll(controller, junks);
                    if (!junks.isEmpty() &&
                            (game.isPlayer(targets.get(0)) ||
                                    game.isIn(controller, targets.get(0), Both_Play))) {
                        game.dealDamage(id, targets.get(0), 2 * junks.size());
                    }
                });

    }
}
