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

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.card.properties.Combat.CombatAbility.Trample;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class Peregrimace extends Unit {

    public Peregrimace(Game game, UUID owner) {
        super(game, "Peregrimace",
                5, new CounterMap(PURPLE, 3),
                "When {~} enters play, draw X cards and lose 2X life where X is equal to number of units you control.",
                4, 4,
                owner, Flying, Trample);

        addEnterPlayEffectOnStack(null, "When {~} enters play, draw X cards and lose 2X life where X is equal to number of units you control.",
                () -> {
                    int x = game.countInZone(controller, Game.Zone.Play, Predicates::isUnit);
                    game.draw(controller, x);
                    game.payHealth(controller, 2 * x);
                });
    }
}
