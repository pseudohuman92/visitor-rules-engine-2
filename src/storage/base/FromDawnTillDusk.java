package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;

public class FromDawnTillDusk extends Ritual {
    public FromDawnTillDusk(Game game, UUID owner) {
        super(game, "From Dawn Till Dusk", 3,
                new CounterMap<>(BLUE, 1),
                "Draw 3 cards.\nDiscard 1 card.",
                owner);

        playable
                .addResolveEffect(() -> {
                    game.draw(playable.card.controller, 3);
                    game.discard(playable.card.controller, 1);
                });
    }
}
