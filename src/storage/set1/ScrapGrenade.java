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

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.BLUE;


/**
 * @author pseudo
 */
public class ScrapGrenade extends Asset {

    public ScrapGrenade(SalvageForge c) {
        super("Scrap Grenade", 3, new Hashmap(BLUE, 3),
                "Purge a Junk from your hand: Deal 3 damage", c.controller);
        copyPropertiesFrom(c);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasIn(controller, Hand, Predicates::isJunk, 1);
    }

    @Override
    public void activate(Game game) {
        Arraylist<UUID> selected = game.selectFromZone(controller, Hand, Predicates::isJunk, 1, false);
        game.purge(controller, selected.get(0));
        UUID target = game.selectDamageTargets(controller, 1, false).get(0);
        game.addToStack(new AbilityCard(this, "Deal 3 damage",
                (y) -> {
                    game.dealDamage(id, target, 3);
                }, new Arraylist<>(target)));
    }
}
