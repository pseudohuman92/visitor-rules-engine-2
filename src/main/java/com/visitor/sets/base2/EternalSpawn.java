/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.*;

/**
 * @author pseudo
 */
public class EternalSpawn extends Unit {

    public EternalSpawn(Game game, UUID owner) {
        super(game, "Eternal Spawn",
                2, new CounterMap(PURPLE, 2),
                "When {~} dies, create a {~}",
                2, 2,
                owner, Combat.CombatAbility.Decay);
        triggering.addEventChecker(EventChecker.selfDeathChecker(game, this, () ->
                new EternalSpawn(game, controller).resolve()));
    }
}
