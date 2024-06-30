package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class YC03 extends Cantrip {
    public YC03(Game game, UUID owner) {
        super(game,
                "YC03",
                4,
                new CounterMap<>(YELLOW, 3),
                "Units you control get +1/+2",
                owner);

        playable
                .addResolveEffect(() ->
                        game.forEachInZone(controller, Game.Zone.Play, Predicates::isUnit,
                                cardId -> game.addAttackAndHealth(cardId, 1, 2)));
    }
}
