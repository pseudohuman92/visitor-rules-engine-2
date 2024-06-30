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

import java.util.UUID;

import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.game.Game.Zone.Void;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class RegressiveHierarchy extends Spell {

    UUID target;

    public RegressiveHierarchy(String owner) {
        super("Regressive Hierarchy", 1, new Hashmap(BLUE, 2), "Purge all copies of a target card from your scrapyard. Deal that many damage.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game) && game.hasIn(controller, Scrapyard, Predicates::any, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Scrapyard, Predicates::any, 1, false);
        target = targets.get(0);


    }

    @Override
    protected void duringResolve(Game game) {
        if (game.isIn(controller, target, Scrapyard)) {
            Card c = game.getCard(target);
            Arraylist<Card> cards = game.extractAllCopiesFrom(controller, c.name, Scrapyard);
            game.putTo(controller, cards, Void);
            UUID target = game.selectDamageTargets(controller, 1, false).get(0);
            game.dealDamage(id, target, cards.size());
        }
    }
}
