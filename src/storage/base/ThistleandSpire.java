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
public class ThistleandSpire extends Unit {

    public ThistleandSpire(Game game, UUID owner) {
        super(game, "Thistle and Spire",
                1, new CounterMap(),
                "{1}, {Use}: {~} deals 1 damage to another unit and 1 damage to itself.",
                0, 4,
                owner, Defender);

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 1, "{1}, {Use}: {~} deals 1 damage to another unit and 1 damage to itself.")
                .setTargeting(Game.Zone.Both_Play, Predicates.anotherUnit(id), 1, 1,
                        targetId -> {
                            game.dealDamage(id, targetId, 1);
                            game.dealDamage(id, id, 1);
                        })
                .setDepleting());
    }
}
