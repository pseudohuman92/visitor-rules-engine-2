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

import static com.visitor.card.properties.Combat.CombatAbility.Haste;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class EndlessHorizon extends Unit {

    public EndlessHorizon(Game game, UUID owner) {
        super(game, "Endless Horizon",
                3, new CounterMap(RED, 2),
                "",
                2, 4,
                owner, Haste);
    }
}
