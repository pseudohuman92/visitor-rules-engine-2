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
import static com.visitor.helpers.Predicates.and;
import static com.visitor.helpers.Predicates.not;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class PoisonWisp extends Unit {

    public PoisonWisp(Game game, UUID owner) {
        super(game, "Poison Wisp",
                6, new CounterMap(PURPLE, 1),
                "When {~} enters play, you may destroy another target unit.",
                5, 5,
                owner, Deathtouch);

        addEnterPlayEffectOnStack(null, "When {~} enters play, you may destroy another target unit.",
                () -> {
                    Arraylist<UUID> destroyedIds = game.selectFromZone(controller, Game.Zone.Both_Play, and(Predicates::isUnit, not(this::equals)), 0, 1, "You may destroy another unit.");
                    if (destroyedIds.size() > 0) {
                        UUID destroyedId = destroyedIds.get(0);
                        game.destroy(id, destroyedId);
                    }
                });
    }
}
