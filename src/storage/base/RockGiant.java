/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Defender;

/**
 * @author pseudo
 */
public class RockGiant extends Unit {

    public RockGiant(Game game, UUID owner) {
        super(game, "Rock Giant",
                3, new CounterMap(),
                "{2}: {~} fights another target unit.",
                3, 3,
                owner, Defender);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 2, "{2}: {~} fights another target unit.")
                .setTargeting(Game.Zone.Both_Play, Predicates.anotherUnit(id), 1, 1,
                        targetId -> game.fight(id, targetId)));
    }
}
