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

import static com.visitor.card.properties.Combat.CombatAbility.Reach;
import static com.visitor.card.properties.Combat.CombatAbility.Regenerate;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class Salamander extends Unit {

    public Salamander(Game game, UUID owner) {
        super(game, "Salamander",
                2, new CounterMap(GREEN, 2),
                "Discard a card: {~} gains +1/+1",
                1, 3,
                owner, Reach, Regenerate);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 0, "{~} gains +1/+1",
                        () -> game.hasIn(controller, Game.Zone.Hand, Predicates::any, 1),
                        () -> game.discard(controller, 1),
                        () -> game.runIfInZone(controller, Game.Zone.Both_Play, id,
                                () -> game.addAttackAndHealth(id, 1, 1))));

    }

}
