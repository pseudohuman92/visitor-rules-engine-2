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

import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class WeakLink extends Spell {

    /**
     * @param owner
     */
    public WeakLink(String owner) {
        super("Weak Link", 2, new Hashmap(BLACK, 2),
                "Opponent chooses an asset he controls, posses that asset.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) &&
                game.hasIn(game.getOpponentName(controller), Play, Predicates::isAsset, 1);
    }

    @Override
    protected void duringResolve(Game game) {
        Arraylist<UUID> selected = game.selectFromZone(game.getOpponentName(controller), Play, Predicates::isAsset, 1, false);
        game.possessTo(controller, selected.get(0), Play);
    }
}
