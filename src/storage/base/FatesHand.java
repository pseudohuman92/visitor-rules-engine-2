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

import static com.visitor.card.properties.Combat.CombatAbility.Deathtouch;
import static com.visitor.card.properties.Combat.CombatAbility.Lifelink;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class FatesHand extends Unit {

    public FatesHand(Game game, UUID owner) {
        super(game, "Fate's Hand",
                1, new CounterMap(PURPLE, 1),
                "Sacrifice a unit: {~} gains +1/+1",
                1, 1,
                owner, Deathtouch, Lifelink);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 0, "{~} gains +1/+1",
                        () -> {
                            UUID sacrificedId = game.selectFromZone(controller, Game.Zone.Play, Predicates::isUnit, 1, 1, "Sacrifice a unit.").get(0);
                            game.sacrifice(sacrificedId);
                        },
                        () -> game.addAttackAndHealth(id, 1, 1)));
    }
}
