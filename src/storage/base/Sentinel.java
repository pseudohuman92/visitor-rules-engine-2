/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.Card;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.helpers.containers.Damage;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Trample;
import static com.visitor.helpers.Predicates.and;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Sentinel extends Unit {

    public Sentinel(Game game, UUID owner) {
        super(game, "Sentinel",
                5, new CounterMap(RED, 3),
                "When {~} enters play, other units you control gain “{Use}: This unit deals 2 damage to any target” until end of turn.",
                5, 3,
                owner, Trample);
        addEnterPlayEffectOnStack(null, "Other units you control gain “{Use}: This unit deals 2 damage to any target” until end of turn.",
                () -> {
                    game.forEachInZone(controller, Game.Zone.Play, and(Predicates::isUnit, c -> !c.id.equals(id)),
                            cardId -> {
                                Card card = game.getCard(cardId);
                                game.addTurnlyActivatedAbility(cardId,
                                        new ActivatedAbility(game, card, 0, "{Use}: This unit deals 2 damage to any target")
                                                .setTargetingForDamage(targetId -> game.dealDamage(card.id, targetId, new Damage(2)))
                                                .setDepleting()
                                );
                            });
                });
    }
}
