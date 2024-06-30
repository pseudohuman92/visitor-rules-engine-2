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
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.helpers.Predicates.anotherUnit;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class SuddenProjectile extends Unit {

    public SuddenProjectile(Game game, UUID owner) {
        super(game, "Sudden Projectile",
                3, new CounterMap(YELLOW, 1),
                "When {~} enters the battlefield, play a 1/1 Spirit with flying.\n" +
                        "{4}, Purge {~}: Purge another target unit.",
                1, 2,
                owner, Flying);
        addEnterPlayEffectOnStack(null, "Play a 1/1 Spirit with flying.", () -> UnitToken.Spirit_1_1(game, controller));
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 4, "{4}, Purge {~}: Purge another target unit.")
                .setTargeting(Game.Zone.Both_Play, anotherUnit(id), 1, 1,
                        targetId -> {
                            game.purge(targetId);
                            game.purge(id);
                        })
                .setPurging());
    }
}
