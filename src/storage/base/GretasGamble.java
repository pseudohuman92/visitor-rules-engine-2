package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;

public class GretasGamble extends Ritual {
    public GretasGamble(Game game, UUID owner) {
        super(game, "Greta's Gamble", 2,
                new CounterMap<>(BLUE, 1),
                "Draw 2 cards",
                owner);

        playable
                .addResolveEffect(() ->
                        game.draw(controller, 2)
                );
    }
}
