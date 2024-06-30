package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.GREEN;

public class GC01 extends Cantrip {
    public GC01(Game game, UUID owner) {
        super(game, "GC01", 1,
                new CounterMap<>(GREEN, 1),
                "Target unit gets +1/+1 until end of turn for each unit you control.",
                owner);

        playable
                .setTargetSingleUnit(null, null, cardId -> {
                    int count = game.countInZone(controller, Play, Predicates::isUnit);
                    game.addTurnlyAttackAndHealth(cardId, count, count);
                }, null);
    }
}
