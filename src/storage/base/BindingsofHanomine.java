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

import static com.visitor.card.properties.Combat.CombatAbility.Vigilance;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class BindingsofHanomine extends Unit {

    public BindingsofHanomine(Game game, UUID owner) {
        super(game, "Bindings of Hanomine",
                4, new CounterMap(YELLOW, 2),
                "",
                3, 5,
                owner, Vigilance);
    }
}
