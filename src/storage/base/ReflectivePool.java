package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;

public class ReflectivePool extends Ritual {
    public ReflectivePool(Game game, UUID owner) {
        super(game, "Reflective Pool", 6,
                new CounterMap<>(BLUE, 3),
                "Return all cards to their controller's hands.",
                owner);

        playable
                .addResolveEffect(game::returnAllCardsToHand);
    }
}
