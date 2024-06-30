/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.properties.Combat;
import com.visitor.card.properties.Triggering;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.Event;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class GD01 extends Unit {

    public GD01(Game game, UUID owner) {
        super(game, "GD01",
                6, new CounterMap(GREEN, 2).add(RED, 2),
                "Whenever an opponent plays a card, create a 1/1 green Insect.",
                5, 6,
                owner, Combat.CombatAbility.Reach);

        triggering = new Triggering(game, this)
                .addEventChecker(new EventChecker(game, this,
                        event -> UnitToken.Insect_1_1(game, controller).resolve())
                        .addTypeChecker(Event.EventType.Play_Card)
                        .addCardChecker(Predicates.controlledBy(game.getOpponentId(controller)))
                        .createAbility("Whenever an opponent plays a card, create a 1/1 green Insect."));
    }
}
