package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;

public class BC07 extends Cantrip {
    public BC07(Game game, UUID owner) {
        super(game, "BC07", 5,
                new CounterMap<>(BLUE, 1),
                "Put target card on top of controller's deck.",
                owner);

        playable
                .setTargetSingleUnit(null, null, game::putToTopOfDeck, null);
    }
}
