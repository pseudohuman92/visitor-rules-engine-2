/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.helpers.containers.Damage;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Haste;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Leapfrog extends Unit {

    Arraylist<UUID> purgeTargets;
    UUID damageTarget;

    public Leapfrog(Game game, UUID owner) {
        super(game, "Leapfrog",
                1, new CounterMap(RED, 1),
                "{1}, {Use}, Purge 2 cards from your discard pile: {~} deals 2 damage to any target.",
                1, 1,
                owner, Haste);

        activatable.addActivatedAbility(new ActivatedAbility(
                game, this, 1,
                "{1}, {Use}, Purge 2 cards from your discard pile: {~} deals 2 damage to any target.")
                .addCanActivateAdditional(() -> game.hasIn(controller, Game.Zone.Discard_Pile, Predicates::any, 2))
                .setTargetingForDamage(targetId -> game.dealDamage(id, targetId, new Damage(2)))
                .addBeforeActivate(() -> {
                    damageTarget = game.selectDamageTargets(controller, 1, 1, "Choose a target to damage.").get(0);
                    purgeTargets = game.selectFromZone(controller, Game.Zone.Discard_Pile, Predicates::any, 2, 1, "Choose 2 cards to purge.");
                    purgeTargets.forEach(cardId -> game.purge(controller, cardId));
                })
                .setDepleting());
    }
}
