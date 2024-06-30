package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;

public class BC01 extends Cantrip {
    public BC01(Game game, UUID owner) {
        super(game, "BC01", 1,
                new CounterMap<>(BLUE, 1),
                "Return target unit to its controller's hands.",
                owner);

        playable.setTargetSingleUnit(null, null, game::returnToHand, null);
    }
}
