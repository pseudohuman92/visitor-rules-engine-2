package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;

public class BC05 extends Cantrip {
    public BC05(Game game, UUID owner) {
        super(game, "BC05", 2,
                new CounterMap<>(BLUE, 1),
                "Target unit gets -2/-0.",
                owner);

        playable
                .setTargetSingleUnit(null, null, cardId -> game.removeAttackAndHealth(cardId, 2, 0), null);
    }
}
