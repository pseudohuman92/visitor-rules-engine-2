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

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class GU04 extends Unit {

    public GU04(Game game, UUID owner) {
        super(game, "GU04",
                5, new CounterMap(BLUE, 2).add(GREEN, 1),
                "When {~} enters play, draw a card for each card you control with cost 3 or greater.",
                4, 4,
                owner);

        addEnterPlayEffectOnStack(null, "When {~} enters play, draw a card for each card you control with cost 3 or greater.",
                () -> game.draw(controller, game.countInZone(controller, Game.Zone.Play,
                        Predicates.and(Predicates::isUnit, c -> c.isPlayable() && (c.getCost() >= 3)))));
    }
}
