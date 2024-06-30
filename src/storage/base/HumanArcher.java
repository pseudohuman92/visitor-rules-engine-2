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

import static com.visitor.helpers.Predicates.and;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class HumanArcher extends Unit {

    public HumanArcher(Game game, UUID owner) {
        super(game, "Human Archer",
                2, new CounterMap(RED, 1),
                "{1}, {Use}, Discard a card: Draw a card.\n" +
                        "{R}{R} - {3}, {Use}, Sacrifice {~}, {~} deals 4 damage to target unit.",
                2, 1,
                owner);
        activatable.addActivatedAbility(
                new ActivatedAbility(game, this, 1, "{1}, {Use}, Discard a card: Draw a card.",
                        () -> game.hasIn(controller, Game.Zone.Hand, Predicates::any, 1),
                        () -> game.discard(controller, 1),
                        () -> game.draw(controller, 1))
                        .setDepleting());

        activatable.addActivatedAbility(
                new ActivatedAbility(game, this, 3, "{3}, {Use}, Sacrifice {~}, {~} deals 4 damage to target unit.")
                        .addCanActivateAdditional(() -> game.hasIn(controller, Game.Zone.Both_Play, and(Predicates::isUnit, c -> !c.id.equals(id)), 1))
                        .setTargeting(Game.Zone.Both_Play, and(Predicates::isUnit, c -> !c.id.equals(id)), 1, 1,
                                targetId -> game.dealDamage(id, targetId, 4))
                        .setDepleting()
                        .setSelfSacrificing()
                        .setKnowledgeRequirement(new CounterMap<>(RED, 2)));
    }
}
