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

import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class RuneofUnnaturalLife extends Asset {

    public RuneofUnnaturalLife(String owner) {
        super("Rune of Unnatural Life", 3, new Hashmap(BLACK, 1),
                "Activate, Sacrifice ~:\n" +
                        "Draw a card from your scrapyard then\n" +
                        "discard a card.", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasIn(controller, Scrapyard, Predicates::any, 1);
    }

    @Override
    public void activate(Game game) {
        Arraylist<UUID> selected = game.selectFromZone(controller, Scrapyard, Predicates::any, 1, false);
        game.sacrifice(id);
        game.addToStack(new AbilityCard(this,
                "Draw a card from your scrapyard then discard a card.",
                (x) -> {
                    if (game.isIn(controller, selected.get(0), Scrapyard)) {
                        game.draw(controller, selected.get(0));
                        game.discard(controller, 1);
                    }
                }, selected));
    }
}
