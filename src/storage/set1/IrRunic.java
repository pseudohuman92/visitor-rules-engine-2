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
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class IrRunic extends Spell {

    /**
     * @param owner
     */
    public IrRunic(String owner) {
        super("Ir-Runic", 1, new Hashmap(BLACK, 1),
                "Opponent discards an spell.", owner);
    }

    @Override
    protected void duringResolve(Game game) {
        Arraylist<UUID> selected = game.selectFromZone(game.getOpponentName(controller), Hand, Predicates::isSpell, 1,
                !game.hasIn(game.getOpponentName(controller), Hand, Predicates::isSpell, 1));
        if (!selected.isEmpty()) {
            game.discard(game.getOpponentName(controller), selected.get(0));
        }
    }

}
