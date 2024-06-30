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

import static com.visitor.card.properties.Combat.CombatAbility.Lifelink;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class Leech extends Unit {

    UUID target;

    public Leech(Game game, UUID owner) {
        super(game, "Leech",
                1, new CounterMap(BLUE, 1),
                "{U}{U} - {2}, Sacrifice {~}: Cancel target unit card.",
                1, 1,
                owner, Lifelink);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 2, "Cancel target unit card.",
                        () -> game.hasIn(controller, Game.Zone.Stack, Predicates::isUnit, 1),
                        () -> target = game.selectFromZone(controller, Game.Zone.Stack, Predicates::isUnit, 1, 1, "Select a unit from stack.").get(0),
                        () -> game.runIfInZone(controller, Game.Zone.Stack, target, () -> game.cancel(target)))
                        .setSelfSacrificing()
                        .setKnowledgeRequirement(new CounterMap<>(BLUE, 2)));
    }
}
