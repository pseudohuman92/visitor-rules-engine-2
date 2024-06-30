/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.*;

/**
 * @author pseudo
 */
public class Assassin extends Unit {

    public Assassin(Game game, UUID owner) {
        super(game, "Assassin",
                1, new CounterMap(PURPLE, 1),
                "",
                1, 1,
                owner, Combat.CombatAbility.Deadly);
    }
}
