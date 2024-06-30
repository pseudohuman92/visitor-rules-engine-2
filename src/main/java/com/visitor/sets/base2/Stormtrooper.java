/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Blitz;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Stormtrooper extends Unit {

    public Stormtrooper(Game game, UUID owner) {
        super(game, "Stormtrooper",
                1, new CounterMap(RED, 1),
                "When {~} dies, deal 1 damage to your opponent.",
                1, 1,
                owner, Blitz);
        triggering.addEventChecker(EventChecker.selfDeathChecker(game, this, () -> game.dealDamage(getId(), game.getOpponentId(controller), 1)));
    }
}
