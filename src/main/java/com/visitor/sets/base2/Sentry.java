/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Vigilance;
import static com.visitor.protocol.Types.Knowledge.*;

/**
 * @author pseudo
 */
public class Sentry extends Unit {

    public Sentry(Game game, UUID owner) {
        super(game, "Sentry",
                1, new CounterMap(YELLOW, 1),
                "",
                1, 1,
                owner, Vigilance);
        addShield(1, false);
    }
}
