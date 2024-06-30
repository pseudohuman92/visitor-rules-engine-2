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

import static com.visitor.card.properties.Combat.CombatAbility.Trample;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class DungBeetle extends Unit {

    public DungBeetle(Game game, UUID owner) {
        super(game, "Dung Beetle",
                1, new CounterMap(GREEN, 1),
                "{2}: Gain +1/+1 until end of turn.",
                1, 1,
                owner, Trample);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 2, "Gain +1/+1 until end of turn.")
                        .setActivate(() -> game.runIfInZone(controller, Game.Zone.Both_Play, id, () -> game.addTurnlyAttackAndHealth(id, 1, 1))));
    }
}
