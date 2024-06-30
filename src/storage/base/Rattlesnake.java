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

import static com.visitor.card.properties.Combat.CombatAbility.Deathtouch;
import static com.visitor.card.properties.Combat.CombatAbility.Reach;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class Rattlesnake extends Unit {

    public Rattlesnake(Game game, UUID owner) {
        super(game, "Rattlesnake",
                2, new CounterMap(GREEN, 2),
                "",
                1, 3,
                owner, Deathtouch, Reach);
    }
}
