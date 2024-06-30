/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.properties.Combat;
import com.visitor.card.properties.Triggering;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.Ability;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.Card;
import com.visitor.game.Event;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class GD02 extends Unit {

    public GD02(Game game, UUID owner) {
        super(game, "GD02",
                5, new CounterMap(GREEN, 2).add(RED, 1),
                "Whenever a unit you control with trample attacks, it gets +2/+2 until end of turn.",
                4, 4,
                owner, Combat.CombatAbility.Trample);

        triggering = new Triggering(game, this)
                .addEventChecker(new EventChecker(game, this,
                        event -> {
                            ((Arraylist<Card>) event.data.get(0)).forEach(card -> {
                                if (card.controller.equals(controller) && card.hasCombatAbility(Combat.CombatAbility.Trample)) {
                                    game.addToStack(new Ability(game, this, "Whenever a unit you control with trample attacks, it gets +2/+2 until end of turn.",
                                            () -> game.addTurnlyAttackAndHealth(card.id, 2, 2)));
                                }

                            });
                        }
                ).addTypeChecker(Event.EventType.Attack));
    }
}
