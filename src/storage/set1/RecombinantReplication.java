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

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static com.visitor.game.Game.Zone.*;
import static com.visitor.protocol.Types.Knowledge.BLUE;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

/**
 * @author pseudo
 */
public class RecombinantReplication extends Spell {


    public RecombinantReplication(String owner) {
        super("Recombinant Replication", 1, new Hashmap(BLUE, 2),
                "Additional Cost - Purge 3 assets from your scrapyard. Transform target asset you control into a copy of target asset in your void.", owner);
    }

    @Override
    public boolean canPlay(Game game) {
        return super.canPlay(game)
                && game.hasIn(controller, Scrapyard, Predicates::isAsset, 5)
                && game.hasIn(controller, Void, Predicates::isAsset, 1)
                && game.hasIn(controller, Play, Predicates::isAsset, 1);
    }

    @Override
    protected void beforePlay(Game game) {
        targets = game.selectFromZone(controller, Scrapyard, Predicates::isAsset, 5, false);
        Arraylist<UUID> sel = game.selectFromZone(controller, Void, Predicates::isAsset, 1, false);
        targets.addAll(sel);
        Arraylist<UUID> sel2 = game.selectFromZone(controller, Play, Predicates::isAsset, 1, false);
        targets.addAll(sel2);


    }

    @Override
    protected void duringResolve(Game game) {
        if (targets.subList(0, 3).parallelStream().allMatch(i -> {
            return game.isIn(controller, i, Scrapyard);
        })
                && game.isIn(controller, targets.get(3), Void)
                && game.isIn(controller, targets.get(4), Play)) {
            try {
                Arraylist<Card> fromScrap = game.extractAll(targets.subList(0, 3));
                game.putTo(controller, fromScrap, Void);
                Card voidCard = game.getCard(targets.get(3));
                Card playCard = game.getCard(targets.get(4));
                Card newCard = voidCard.getClass().getDeclaredConstructor(String.class).newInstance(playCard.controller);
                newCard.copyPropertiesFrom(playCard);
                game.transformTo(this, playCard, newCard);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                getLogger(RecombinantReplication.class.getName()).log(SEVERE, null, ex);
            }
        }
    }
}
