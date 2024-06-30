/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.test;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

/**
 * @author pseudo
 */
public class SingleActivatedAbility extends Unit {

    public SingleActivatedAbility(Game game, UUID owner) {
        super(game, "Single Activated Ability",
                0, new CounterMap(),
                "{0}: Gain +0/+1",
                0, 1,
                owner);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "Gain +0/+1",
                () -> game.runIfInPlay(getId(), () -> game.addAttackAndHealth(getId(), 0, 1, false))));
    }
}
