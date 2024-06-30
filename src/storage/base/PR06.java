package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class PR06 extends Ritual {

    public PR06(Game game, UUID owner) {
        super(game, "PR06", 3,
                new CounterMap<>(PURPLE, 1),
                "Target player discards 2 cards",
                owner);

        playable
                .setTargetSinglePlayer(playerId -> game.discard(playerId, 2));
    }
}
