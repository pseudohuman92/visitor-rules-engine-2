/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.Ability;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class GP03 extends Unit {

    public GP03(Game game, UUID owner) {
        super(game, "GP03",
                2, new CounterMap(GREEN, 1).add(PURPLE, 1),
                "Whenever {~} deals combat damage to a player, {~} gains +1/+1.",
                1, 1,
                owner, Combat.CombatAbility.Haste);

        combat.addDamageEffect((targetId, damage) -> {
            if (game.isPlayer(targetId) && damage.combat) {
                game.addToStack(new Ability(game, this, "Whenever {~} deals combat damage to a player, {~} gains +1/+1.",
                        () -> game.addAttackAndHealth(id, 1, 1)));
            }
        });
    }

}
