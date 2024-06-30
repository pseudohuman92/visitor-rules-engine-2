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
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class GP02 extends Unit {

    public GP02(Game game, UUID owner) {
        super(game, "GP02",
                6, new CounterMap(GREEN, 2).add(PURPLE, 2),
                "When {~} enters play, Create a 1/1 black and green Insect for each unit card in your discard pile.\n" +
                        "{2}, Sacrifice another unit: You gain 1 life and draw a card.",
                2, 3,
                owner);

        addEnterPlayEffectOnStack(null, "When {~} enters play, Create a 1/1 black and green Insect for each unit card in your discard pile.",
                () -> {
                    int unitCount = game.countInZone(controller, Game.Zone.Discard_Pile, Predicates::isUnit);
                    for (int i = 0; i < unitCount; i++) {
                        UnitToken.Insect_1_1(game, controller).resolve();
                    }
                });

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 2, "{2}, Sacrifice another unit: You gain 1 life and draw a card.")
                .setTargetingForSacrifice(Game.Zone.Play, Predicates.anotherUnit(id), 1, 1,
                        targetId -> {
                            game.draw(controller, 1);
                            game.gainHealth(controller, 1);
                        }));
    }
}
