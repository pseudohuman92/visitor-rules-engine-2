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
import com.visitor.helpers.containers.Damage;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class DivineLight extends Unit {

    UUID target;

    public DivineLight(Game game, UUID owner) {
        super(game, "Divine Light",
                5, new CounterMap(RED, 1),
                "{R}{R}{R} - {1}, purge top 10 cards of your deck: {~} deals 2 damage to any target.",
                3, 3,
                owner, Flying);

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 1, "{~} deals 2 damage to any target.",
                () -> game.getDeckSize(controller) >= 10,
                () -> {
                    target = game.selectDamageTargets(controller, 1, 1, "Choose a damage target.").get(0);
                    game.purgeFromDeck(controller, 10);
                },
                () -> game.dealDamage(id, target, new Damage(2)))
                .setKnowledgeRequirement(new CounterMap<>(RED, 3)));
    }
}
