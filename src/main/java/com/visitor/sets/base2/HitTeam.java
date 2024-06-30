/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class HitTeam extends Unit {

    public HitTeam(Game game, UUID owner) {
        super(game, "Hit Team",
                2, new CounterMap(RED, 1),
                "Whenever {~} attacks, deal 1 damage to your opponent.",
                2, 2,
                owner);
        triggering.addEventChecker(EventChecker.attackChecker(game, this,
                l -> l.hasOne(c-> c.getId().equals(getId())),
                l -> game.dealDamage(getId(), game.getOpponentId(controller), 1)));

    }
}
