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

import static com.visitor.card.properties.Combat.CombatAbility.Defender;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class PolarVortex extends Unit {

    public PolarVortex(Game game, UUID owner) {
        super(game, "Polar Vortex",
                1, new CounterMap(YELLOW, 1),
                "",
                2, 3,
                owner, Defender);
    }
}
