package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class PC04 extends Cantrip {
    public PC04(Game game, UUID owner) {
        super(game, "PC04", 3,
                new CounterMap<>(PURPLE, 2),
                "Destroy target unit.",
                owner);

        playable
                .setTargetSingleUnit(null, null, cardId -> game.destroy(id, cardId), null);
    }
}
