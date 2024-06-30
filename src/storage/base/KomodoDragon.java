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

import static com.visitor.card.properties.Combat.CombatAbility.Deathtouch;
import static com.visitor.card.properties.Combat.CombatAbility.Trample;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class KomodoDragon extends Unit {

    public KomodoDragon(Game game, UUID owner) {
        super(game, "Komodo Dragon",
                3, new CounterMap(GREEN, 3),
                "{5}: Units you control gains +3/+3 and Trample until end of turn",
                3, 2,
                owner, Deathtouch, Trample);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 5, "Units you control gains +3/+3 and Trample until end of turn",
                        () -> game.forEachInZone(controller, Game.Zone.Play, Predicates::isUnit,
                                (cardId) -> {
                                    game.addTurnlyAttackAndHealth(cardId, 3, 3);
                                    game.addTurnlyCombatAbility(cardId, Trample);
                                })));
    }
}
