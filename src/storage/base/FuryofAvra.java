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

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class FuryofAvra extends Unit {

    public FuryofAvra(Game game, UUID owner) {
        super(game, "Fury of Avra",
                4, new CounterMap(PURPLE, 1),
                "{P}{P} - {2}, Pay 2 health: Draw a card.",
                3, 1,
                owner, Flying);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 2, "Draw a card",
                        () -> game.hasHealth(controller, 2),
                        () -> game.payHealth(controller, 2),
                        () -> game.draw(controller, 1))
                        .setKnowledgeRequirement(new CounterMap<>(PURPLE, 2)));
    }
}
