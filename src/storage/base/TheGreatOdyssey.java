package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;

public class TheGreatOdyssey extends Ritual {
    public TheGreatOdyssey(Game game, UUID owner) {
        super(game, "The Great Odyssey", 6,
                new CounterMap<>(BLUE, 2),
                "Return up to 3 target units to their controller's hands.",
                owner);

        playable.setTargetMultipleUnits(null, 0, 3, game::returnToHand, null);
    }
}
