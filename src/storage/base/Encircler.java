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
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Encircler extends Unit {

    public Encircler(Game game, UUID owner) {
        super(game, "Encircler",
                3, new CounterMap(RED, 1),
                "{R}{R} - {Use}: Destroy target unit. That unit's controller gains control of {~}.",
                2, 3,
                owner, Reach);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{Use}: Destroy target unit. That unit's controller gains control of {~}.")
                .setTargeting(Game.Zone.Both_Play, Predicates::isUnit, 1, 1,
                        (targetId) -> {
                            UUID newController = game.getCard(targetId).controller;
                            game.destroy(id, targetId);
                            if (!controller.equals(newController))
                                game.gainControlFromZone(game.getOpponentId(newController), Game.Zone.Play, Game.Zone.Play, id);
                        })
                .setDepleting()
                .setKnowledgeRequirement(new CounterMap<>(RED, 2))
        );
    }
}
