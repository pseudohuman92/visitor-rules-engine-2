/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.card.properties.Combat.CombatAbility.Haste;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class StarlingSwarm extends Unit {

    public StarlingSwarm(Game game, UUID owner) {
        super(game, "Starling Swarm",
                6, new CounterMap(RED, 3),
                "Discard another card named {~}: {~} gets +X/+X until end of turn, where X is its attack.",
                4, 4,
                owner, Flying, Haste);

        activatable.addActivatedAbility(new ActivatedAbility(
                game, this, 0, "{~} gets +X/+X until end of turn, where X is its attack.",
                () -> game.hasIn(controller, Game.Zone.Hand, c -> c.name.equals(name), 1),
                () -> {
                    UUID target = game.selectFromZone(controller, Game.Zone.Hand, c -> c.name.equals(name), 1, 1, "Discard a " + name + " card.").get(0);
                    game.discard(controller, target);
                },
                () -> game.addAttackAndHealth(id, getAttack(), getAttack(), true)
        ));
    }
}
