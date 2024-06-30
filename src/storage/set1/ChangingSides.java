/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Ally;
import com.visitor.card.types.Card;
import com.visitor.card.types.Ritual;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class ChangingSides extends Ritual {

    public ChangingSides(Game game, String owner) {
        super(game, "Changing Sides", 4, new Hashmap(GREEN, 2),
                "Possess target ally with no loyalty counters on it. \n" +
                        "Place 1 loyalty on possessed ally.", owner);

        playable.setCanPlayAdditional(() -> game.hasIn(controller, Both_Play, ChangingSides::validTarget, 1))
                .setBeforePlay(() -> {
                    targets = game.selectFromZone(controller, Both_Play, ChangingSides::validTarget, 1, false);
                })
                .setResolveEffect(() -> {
                    if (game.isIn(controller, targets.get(0), Both_Play)) {
                        game.possessTo(controller, targets.get(0), Both_Play);
                    }
                });
    }

    public static boolean validTarget(Card c) {
        return c.types.contains(CardType.Ally) &&
                ((Ally) c).loyalty == 0;
    }
}

