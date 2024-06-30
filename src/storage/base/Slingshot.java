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

import static com.visitor.card.properties.Combat.CombatAbility.FirstStrike;
import static com.visitor.card.properties.Combat.CombatAbility.Flying;

/**
 * @author pseudo
 */
public class Slingshot extends Unit {

    public Slingshot(Game game, UUID owner) {
        super(game, "Slingshot",
                4, new CounterMap(),
                "",
                2, 1,
                owner, Flying, FirstStrike);
    }
}
