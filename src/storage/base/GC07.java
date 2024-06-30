package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;

public class GC07 extends Cantrip {
    public GC07(Game game, UUID owner) {
        super(game, "GC07", 1,
                new CounterMap<>(GREEN, 2),
                "Draw the top unit from your deck.",
                owner);

        playable
                .addResolveEffect(() ->
                        game.draw(controller, game.extractTopmostMatchingFromDeck(controller, Predicates::isUnit))
                );
    }
}
