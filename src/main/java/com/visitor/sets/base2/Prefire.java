/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Asset;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.Card;
import com.visitor.game.Event;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Prefire extends Asset {

    public Prefire(Game game, UUID owner) {
        super(game, "Prefire",
                2, new CounterMap(RED, 1),
                "Whenever you play an {R} card, deal 1 damage to your opponent.",
                owner);
        triggering.addEventChecker(EventChecker.playChecker(game, this,
                c -> c.controller.equals(controller) && c.knowledge.contains(RED),
                c -> game.dealDamage(getId(), game.getOpponentId(controller), 1)));
    }
}
