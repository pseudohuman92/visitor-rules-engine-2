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

import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class ShieldofNorn extends Unit {

    UUID target;

    public ShieldofNorn(Game game, UUID owner) {
        super(game, "Shield of Norn",
                4, new CounterMap(PURPLE, 2),
                "{P}{P}{P}{P} - {Slow} | {5}, {Use}: Resurrect target unit.",
                4, 4,
                owner);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 5, "Resurrect target unit.",
                        () -> game.hasIn(controller, Game.Zone.Discard_Pile, Predicates::isUnit, 1),
                        () -> target = game.selectFromZone(controller, Game.Zone.Discard_Pile, Predicates::isUnit, 1, 1, "Choose a unit.").get(0),
                        () -> game.resurrect(target))
                        .setDepleting()
                        .setSlow()
                        .setKnowledgeRequirement(new CounterMap<>(PURPLE, 4)));
    }
}
