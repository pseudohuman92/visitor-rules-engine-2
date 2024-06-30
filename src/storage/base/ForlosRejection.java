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

import static com.visitor.helpers.Predicates.and;

/**
 * @author pseudo
 */
public class ForlosRejection extends Unit {

    public ForlosRejection(Game game, UUID owner) {
        super(game, "Forlo's Rejection",
                2, new CounterMap(),
                "{Use}: Each colorless unit you control gains +1/+1",
                2, 1,
                owner);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{Use}: Each colorless unit you control gains +1/+1",
                () -> game.forEachInZone(controller, Game.Zone.Play, and(Predicates::isUnit, Predicates::isColorless),
                        cardId -> game.addAttackAndHealth(cardId, 1, 1)))
                .setDepleting());
    }
}
