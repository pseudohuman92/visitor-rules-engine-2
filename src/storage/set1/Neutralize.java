/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.Spell;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.game.Game.Zone.Stack;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class Neutralize extends Spell {

    UUID target;

    public Neutralize(String owner) {
        super("Neutralize", 3, new Hashmap(YELLOW, 2),
                "Cancel target card.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Stack, c -> {
            return !(c instanceof AbilityCard);
        }, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Stack, c -> {
            return !(c instanceof AbilityCard);
        }, 1, false);
        target = targets.get(0);


    }

    @Override
    protected void duringResolve(Game game) {
        Card c = game.extractCard(target);
        game.putTo(c.controller, c, Scrapyard);
    }
}
