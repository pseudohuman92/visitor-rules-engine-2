/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class BreathofZephyrus extends Unit {

    public BreathofZephyrus(Game game, UUID owner) {
        super(game, "Breath of Zephyrus",
                3, new CounterMap(PURPLE, 1),
                "",
                2, 1,
                owner, Flying);
    }
}
