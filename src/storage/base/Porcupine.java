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

import static com.visitor.card.properties.Combat.CombatAbility.Defender;
import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.helpers.Predicates.and;
import static com.visitor.helpers.Predicates.not;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class Porcupine extends Unit {

    UUID target;

    public Porcupine(Game game, UUID owner) {
        super(game, "Porcupine",
                2, new CounterMap(BLUE, 1),
                "{U}{U} - {2}: Deplete another target unit without flying.",
                1, 5,
                owner, Defender);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 2, "Deplete target unit without flying.",
                        () -> game.hasIn(controller, Game.Zone.Both_Play, and(Predicates::isUnit, not(Predicates::isDepleted), c -> !c.hasCombatAbility(Flying), not(this::equals)), 1),
                        () -> target = game.selectFromZone(controller, Game.Zone.Both_Play, and(Predicates::isUnit, not(Predicates::isDepleted), c -> !c.hasCombatAbility(Flying), not(this::equals)), 1, 1, "Select a non-flying ready unit.").get(0),
                        () -> game.runIfInZone(controller, Game.Zone.Both_Play, target, () -> game.deplete(target)))
                        .setKnowledgeRequirement(new CounterMap<>(BLUE, 2)));
    }
}
