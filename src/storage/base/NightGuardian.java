/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Vigilance;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class NightGuardian extends Unit {

    public NightGuardian(Game game, UUID owner) {
        super(game, "Night Guardian",
                5, new CounterMap(YELLOW, 3),
                "{3}, {Use}: Target unit gains shield 2.",
                4, 6,
                owner, Vigilance);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 3, "{3}, {Use}: Target unit gains shield 2.")
                .setTargeting(Game.Zone.Both_Play, Predicates::isUnit, 1, 1,
                        targetId -> game.runIfInPlay(targetId, () -> game.addShield(targetId, 2)))
                .setDepleting());
    }
}
