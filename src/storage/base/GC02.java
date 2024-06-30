package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;

public class GC02 extends Cantrip {
    public GC02(Game game, UUID owner) {
        super(game, "GC02", 1,
                new CounterMap<>(GREEN, 1),
                "Target unit heals 3.",
                owner);

        playable
                .setTargetSingleUnit(null, null, cardId -> game.heal(cardId, 3), null);
    }
}
