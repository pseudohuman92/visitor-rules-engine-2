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

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class FireSprite extends Unit {

    public FireSprite(Game game, UUID owner) {
        super(game, "Fire Sprite",
                5, new CounterMap(PURPLE, 2),
                "Sacrifice a unit: {~} gains +2/+2 until end of turn.",
                3, 4,
                owner, Flying);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 0, "{~} gains +2/+2 until end of turn.",
                        () -> {
                            UUID sacrificedId = game.selectFromZone(controller, Game.Zone.Play, Predicates::isUnit, 1, 1, "Sacrifice a unit.").get(0);
                            game.sacrifice(sacrificedId);
                        },
                        () -> game.addTurnlyAttackAndHealth(id, 2, 2)));
    }
}
