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

import static com.visitor.card.properties.Combat.CombatAbility.Trample;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class PorciniatheGreat extends Unit {

    public PorciniatheGreat(Game game, UUID owner) {
        super(game, "Porcinia the Great",
                1, new CounterMap(RED, 1),
                "{Use}: Target opponent gains control of target unit you control.",
                1, 1,
                owner, Trample);

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{Use}: Target opponent gains control of target unit you control.")
                .setTargeting(Game.Zone.Play, Predicates.and(Predicates::isUnit, c -> c.controller.equals(controller)), 1, 1,
                        targetId -> game.gainControlFromZone(game.getOpponentId(controller), Game.Zone.Play, Game.Zone.Play, targetId))
                .setDepleting());
    }
}
