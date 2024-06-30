/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.Event;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class GY03 extends Unit {

    public GY03(Game game, UUID owner) {
        super(game, "GY03",
                6, new CounterMap(YELLOW, 2).add(GREEN, 2),
                "When {~} enters the battlefield, create a 5/5 green Wurm with trample.",
                5, 5,
                owner, Combat.CombatAbility.Trample);

        addEnterPlayEffectOnStack(null, "When {~} enters the battlefield, create a 5/5 green Wurm with trample.",
                () -> {
                    UnitToken.Wurm_5_5(game, controller).resolve();
                });
        triggering.addEventChecker(new EventChecker(game, this,
                event ->
                        game.addTurnlyAttackAndHealth(id,
                                game.countInZone(controller, Game.Zone.Play, Predicates.and(Predicates::isUnit, Predicates::isReady)),
                                game.countInZone(controller, Game.Zone.Play, Predicates.and(Predicates::isUnit, Predicates::isReady))))
                .addTypeChecker(Event.EventType.Attack)
                .addCardListChecker(cardlist -> cardlist.contains(this))
                .createAbility("Whenever {~} attacks, it gets +1/+1 until end of turn for each ready unit you control."));
    }
}
