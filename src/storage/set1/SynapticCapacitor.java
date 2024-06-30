/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.game.Game.Zone.Scrapyard;
import static com.visitor.protocol.Types.Knowledge.BLUE;


/**
 * @author pseudo
 */
public class SynapticCapacitor extends Asset {

    public SynapticCapacitor(String owner) {
        super("Synaptic Capacitor", 2, new Hashmap(BLUE, 2),
                "Discard 1, Purge 1: Gain 1 Energy", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasIn(controller, Hand, Predicates::any, 1)
                && !depleted;
    }

    @Override
    public void activate(Game game) {
        game.discard(controller, 1);
        UUID target = game.selectFromZone(game.getPlayer(controller).username, Scrapyard, Predicates::any, 1, false).get(0);
        if (game.hasIn(controller, Scrapyard, Predicates::any, 1)) {
            game.purge(controller, target);
        }
        game.addToStack(new AbilityCard(this, controller + " gains 1 energy",
                (x) -> {
                    game.addEnergy(controller, 1);
                }));
    }
}
