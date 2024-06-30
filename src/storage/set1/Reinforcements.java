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
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Reinforcements extends Spell {

    UUID target;

    public Reinforcements(String owner) {
        super("Reinforcements", 1, new Hashmap(RED, 1),
                "Additional Cost \n" +
                        "  Shuffle a card from your hand to your deck. \n" +
                        "Draw two cards.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Hand, Predicates::any, 2);
    }

    @Override
    protected void beforePlay(Game game) {
        target = game.selectFromZone(controller, Hand, c -> {
            return !c.id.equals(id);
        }, 1, false).get(0);
        game.shuffleIntoDeck(controller, new Arraylist<>(game.extractCard(target)));
    }

    @Override
    protected void duringResolve(Game game) {
        game.draw(controller, 2);
    }
}
