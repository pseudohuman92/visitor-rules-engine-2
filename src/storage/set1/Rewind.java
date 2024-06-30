/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;
import com.visitor.protocol.Types;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.game.Game.Zone.Void;
import static com.visitor.protocol.Types.Knowledge.YELLOW;
import static java.lang.Math.min;

/**
 * @author pseudo
 */
public class Rewind extends Spell {

    int x;

    public Rewind(String owner) {
        super("Rewind", 0, new Hashmap(YELLOW, 2),
                "Recover X. \n" +
                        "Purge ~.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game)
                && game.hasEnergy(controller, 2)
                && game.hasIn(controller, Scrapyard, Predicates::any, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        x = game.selectX(controller, min(game.getPlayer(controller).energy / 2,
                game.getZone(controller, Scrapyard).size()));
        game.spendEnergy(controller, 2 * x);
        text = "Recover " + x + ". \n" +
                "Purge ~.";

    }

    @Override
    protected void duringResolve(Game game) {
        Arraylist<UUID> selected = game.selectFromZone(controller, Scrapyard, Predicates::any, x, false);
        Arraylist<Card> cards = game.extractAll(selected);
        game.shuffleIntoDeck(controller, cards);
        text = "Recover X. \n" +
                "Purge ~.";
        game.putTo(controller, this, Void);
    }

    @Override
    public Types.Card.Builder toCardMessage() {
        return super.toCardMessage().setCost("XX");
    }
}
