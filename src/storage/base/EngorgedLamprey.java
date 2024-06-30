/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Deathtouch;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class EngorgedLamprey extends Unit {

    public EngorgedLamprey(Game game, UUID owner) {
        super(game, "Engorged Lamprey",
                3, new CounterMap(PURPLE, 1),
                "{P}{P}{P} - When {~} enters play, you may sacrifice a unit, if you do, draw X cards and gain X health where X is that unit's attack.",
                2, 2,
                owner, Deathtouch);

        addEnterPlayEffectOnStack(
                new CounterMap<>(PURPLE, 3),
                "When {~} enters play, you may sacrifice a unit, if you do, draw X cards and gain X health where X is that unit's attack.",
                () -> {
                    Arraylist<UUID> sacrificedIds = game.selectFromZone(controller, Game.Zone.Play, Predicates::isUnit, 0, 1, "You may sacrifice a unit.");
                    if (sacrificedIds.size() > 0) {
                        UUID sacrificedId = sacrificedIds.get(0);
                        int attack = game.getAttack(sacrificedId);
                        game.sacrifice(sacrificedId);
                        game.draw(controller, attack);
                        game.gainHealth(controller, attack);
                    }
                });
    }
}
