package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Deck;
import static com.visitor.protocol.Types.Knowledge.GREEN;

public class GC06 extends Cantrip {
    public GC06(Game game, UUID owner) {
        super(game, "GC06", 5,
                new CounterMap<>(GREEN, 3),
                "Choose and draw up to 2 units from your deck.",
                owner);

        playable
                .setTargetMultipleCards(Deck, Predicates::isUnit, 0, 2,
                        "Select up to 2 units from your deck.", cardId -> game.draw(controller, cardId), () -> game.shuffleDeck(controller));
    }
}
