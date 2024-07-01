/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Evasive;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class Dropship extends Unit {

    public Dropship(Game game, UUID owner) {
        super(game, "Dropship",
                4, new CounterMap(YELLOW, 1),
                "{Y}{Y}{Y} - {2}: Create a Sentry.",
                2, 4,
                owner, Evasive);
        addShield(2, false);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 2, "{Y}{Y}{Y} - {2}: Create a Sentry.",
                () -> new Sentry(game, controller).resolve())
                .setKnowledgeRequirement(new CounterMap<>(YELLOW, 3)));
    }
}
