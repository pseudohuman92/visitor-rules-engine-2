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

import static com.visitor.game.Game.Zone.Stack;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Withdrawal extends Spell {

    UUID target;

    public Withdrawal(String owner) {
        super("Withdrawal", 2, new Hashmap(RED, 1),
                "Cancel target spell.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Stack, Predicates::isSpell, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Stack, Predicates::isSpell, 1, false);
        target = targets.get(0);


    }

    @Override
    protected void duringResolve(Game game) {
        game.getCard(target).returnToHand(game);
    }
}
