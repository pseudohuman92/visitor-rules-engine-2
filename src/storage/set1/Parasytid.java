/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Both_Play;
import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class Parasytid extends Asset {

    public Parasytid(String owner) {
        super("Parasytid", 2, new Hashmap(BLACK, 2),
                "3, Sacrifice ~, Activate: Possess target asset.", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return !depleted;
    }

    @Override
    public void activate(Game game) {
        game.deplete(id);
        game.spendEnergy(controller, 3);
        Arraylist<UUID> selected = game.selectFromZone(controller, Both_Play, Predicates::isAsset, 1, false);
        game.sacrifice(id);
        game.addToStack(new AbilityCard(this,
                "Possess target asset",
                (x) -> {
                    if (game.isIn(controller, selected.get(0), Both_Play)) {
                        game.possessTo(controller, selected.get(0), Play);
                    }
                }, selected));
    }
}
