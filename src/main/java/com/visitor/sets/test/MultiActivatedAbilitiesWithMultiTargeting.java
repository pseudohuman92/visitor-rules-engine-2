/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.test;

import com.visitor.card.properties.Targetable;
import com.visitor.card.types.Unit;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.helpers.Predicates.or;

/**
 * @author pseudo
 */
public class MultiActivatedAbilitiesWithMultiTargeting extends Unit {

    public MultiActivatedAbilitiesWithMultiTargeting(Game game, UUID owner) {
        super(game, "Multi Activated Abilities With Multi Targeting",
                0, new CounterMap(),
                "{0}: Deal 1 damage to a target. Deal 1 damage to a target.\n{0}: Gain +1/+0",
                0, 1,
                owner);

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{0}: Deal 1 damage to a target. Deal 1 damage to a target.")
                .addTargeting(Base.Zone.Both_Play_With_Players, Targetable::isDamagable, 1, 1,
                        "Deal 1 damage to a target.",
                        c -> game.dealDamage(getId(), c, 1), false)
                .addTargeting(Base.Zone.Both_Play_With_Players, Targetable::isDamagable, 1, 1,
                        "Deal 1 damage to a target.",
                        c -> game.dealDamage(getId(), c, 1), false));
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{0}: Gain +1/+0", () -> game.runIfInPlay(getId(), () -> game.addAttackAndHealth(getId(), 1, 0, false))));
    }
}
