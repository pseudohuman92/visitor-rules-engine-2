/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.Card;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.game.Game.Zone.Play;
import static com.visitor.helpers.UUIDHelper.getInList;
import static com.visitor.protocol.Types.Counter.CHARGE;
import static com.visitor.protocol.Types.Knowledge.BLUE;


/**
 * @author pseudo
 */
public class GarbageWeapon extends Asset {


    UUID target;

    public GarbageWeapon(String owner) {
        super("Garbage Weapon", 3, new Hashmap(BLUE, 1),
                "Discard a Junk: Charge 1.\n" +
                        "Sacrifice an Asset: Charge 1. \n" +
                        "If it has 3 charges, transform ~ into Trash Gun", owner);
    }

    public GarbageWeapon(TrashGun c) {
        super("Garbage Weapon", 3, new Hashmap(BLUE, 1),
                "Discard a Junk: Charge 1.\n" +
                        "Sacrifice an Asset: Charge 1. \n" +
                        "If it has 3 charges, transform ~ into Trash Gun", c.controller);
        copyPropertiesFrom(c);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasIn(controller, Hand, Predicates::isJunk, 1)
                || game.hasIn(controller, Play, Predicates::isAsset, 1);
    }

    @Override
    public void activate(Game game) {
        Arraylist<Card> choices = new Arraylist<>();
        if (game.hasIn(controller, Hand, Predicates::isJunk, 1)) {
            choices.add(new AbilityCard(this, "Discard a Junk: Charge 1.",
                    (x1) -> {
                        target = game.selectFromZone(controller, Hand, Predicates::isJunk, 1, false).get(0);
                        game.discard(controller, target);
                        game.addToStack(new AbilityCard(this, "Charge 1",
                                (x2) -> {
                                    addCounters(CHARGE, 1);
                                    if (counters.get(CHARGE) >= 3) {
                                        game.transformTo(this, this, new TrashGun(this));
                                    }
                                }));
                    }));
        }
        if (game.hasIn(controller, Play, Predicates::isAsset, 1)) {
            choices.add(new AbilityCard(this, "Sacrifice an Asset: Charge 1.",
                    (x1) -> {
                        target = game.selectFromZone(controller, Play, Predicates::isAsset, 1, false).get(0);
                        game.sacrifice(target);
                        game.addToStack(new AbilityCard(this, "Charge 1",
                                (x2) -> {
                                    addCounters(CHARGE, 1);
                                    if (counters.get(CHARGE) >= 3) {
                                        game.transformTo(this, this, new TrashGun(this));
                                    }
                                }));
                    }));
        }
        Arraylist<UUID> selection = game.selectFromList(controller, choices, Predicates::any, 1, false);
        getInList(choices, selection).get(0).resolve(game);
    }
}