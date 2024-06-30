/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.Ritual;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class Recombobulate extends Ritual {

    public Recombobulate(String owner) {
        super("Recombobulate", 4, new Hashmap(BLUE, 3),
                "Shuffle all junk from your hand into your deck and draw that many cards.", owner);
    }

    @Override
    protected void duringResolve(Game game) {
        Arraylist<Card> junks = game.getAllFrom(controller, Hand, Predicates::isJunk);
        game.shuffleIntoDeck(controller, junks);
        game.draw(controller, junks.size());
    }
}
