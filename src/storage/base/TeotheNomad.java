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

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.card.properties.Combat.CombatAbility.Vigilance;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class TeotheNomad extends Unit {

    public TeotheNomad(Game game, UUID owner) {
        super(game, "Teo the Nomad",
                5, new CounterMap<>(YELLOW, 2),
                "{Y}{Y}{Y}{Y}{Y} - {7}: Resurrect target unit.",
                3, 2,
                owner, Flying, Vigilance);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 7, "{Y}{Y}{Y}{Y}{Y} - {7}: Resurrect target unit.")
                .setTargeting(Game.Zone.Discard_Pile, Predicates::isUnit, 1, 1, targetId -> game.runIfInZone(controller, Game.Zone.Discard_Pile, targetId, () -> game.resurrect(targetId)))
                .setKnowledgeRequirement(new CounterMap<>(YELLOW, 5)));
    }


}
