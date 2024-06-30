package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.helpers.Predicates.or;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class YR01 extends Ritual {

    public YR01(Game game, UUID owner) {
        super(game, "YR01", 2,
                new CounterMap<>(YELLOW, 1),
                "Destroy target purple or red unit.",
                owner);

        playable
                .setTargetSingleUnit(null, or(Predicates::isPurple, Predicates::isRed), cardId -> game.destroy(id, cardId), null);
    }
}
