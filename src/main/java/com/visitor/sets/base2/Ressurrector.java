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

import static com.visitor.helpers.Predicates.isUnit;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class Ressurrector extends Unit {

    public Ressurrector(Game game, UUID owner) {
        super(game, "Ressurrector",
                5, new CounterMap(PURPLE, 3),
                "Whenever a unit dies, if it has more than 0 health, create a Zombie",
                1, 8,
                owner, Combat.CombatAbility.Deadly);
        triggering.addEventChecker(EventChecker.deathChecker(game, this,
            c -> {
                if (isUnit(c) && c.getHealth() > 0){
                    UnitToken.Zombie_2_2(game, controller).resolve();
                }
        }));
    }
}
