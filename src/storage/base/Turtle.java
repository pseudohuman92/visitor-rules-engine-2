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
import static com.visitor.helpers.Predicates.and;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class Turtle extends Unit {

    UUID target;

    public Turtle(Game game, UUID owner) {
        super(game, "Turtle",
                1, new CounterMap(BLUE, 1),
                "{U}{U}{U}{U}{U} - {Slow} | {5}, {Use}: Gain control of target depleted unit.",
                0, 5,
                owner, Defender);

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 5, "Gain control of target depleted unit.",
                () -> game.hasIn(game.getOpponentId(controller), Game.Zone.Play, and(Predicates::isUnit, Predicates::isDepleted), 1),
                () -> target = game.selectFromZone(game.getOpponentId(controller), Game.Zone.Play, and(Predicates::isUnit, Predicates::isDepleted), 1, 1, "Select a depleted unit opponent controls.").get(0),
                () -> game.gainControlFromZone(game.getOpponentId(controller), Game.Zone.Play, Game.Zone.Play, target))
                .setDepleting()
                .setSlow()
                .setKnowledgeRequirement(new CounterMap<>(BLUE, 5)));
    }

}
