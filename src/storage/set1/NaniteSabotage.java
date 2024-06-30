/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class NaniteSabotage extends Spell {

    /**
     * @param owner
     */
    public NaniteSabotage(String owner) {
        super("Nanite Sabotage", 2, new Hashmap(BLUE, 2),
                "Choose an asset from opponent's hand and transform it into Junk", owner);
    }

    @Override
    protected void duringResolve(Game game) {
        Arraylist<UUID> s = game.selectFromList(controller,
                game.getZone(game.getOpponentName(controller), Hand),
                Predicates::isAsset, 1, true);
        if (!s.isEmpty()) {
            game.transformToJunk(this, s.get(0));
        }
    }

}
