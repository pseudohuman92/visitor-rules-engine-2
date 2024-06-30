/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class RavenousVampire extends Unit {

    public RavenousVampire(Game game, UUID owner) {
        super(game, "Ravenous Vampire",
                6, new CounterMap(PURPLE, 3),
                "When {~} deals damage to a player, create that many Bats",
                6, 6,
                owner, Combat.CombatAbility.Drain, Combat.CombatAbility.Evasive);
        combat.addDamageEffect(
                (target, damage) -> {
                if (game.isPlayer(target)){
                    for (int i = 0; i < damage.amount; i++)
                        UnitToken.Bat_1_1(game, controller).resolve();
                }
        });
    }
}
