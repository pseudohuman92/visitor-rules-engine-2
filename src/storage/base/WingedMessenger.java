/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class WingedMessenger extends Unit {

    public WingedMessenger(Game game, UUID owner) {
        super(game, "Winged Messenger",
                2, new CounterMap(YELLOW, 1),
                "{1} {Use}: Create 1/1 Warrior.",
                1, 3,
                owner, Flying);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 1, "{1} {Use}: Create 1/1 Warrior.",
                () -> UnitToken.Warrior_1_1(game, controller).resolve())
                .setDepleting());
    }
}
