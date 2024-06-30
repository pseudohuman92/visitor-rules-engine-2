package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class YR05 extends Ritual {

    public YR05(Game game, UUID owner) {
        super(game, "YR05", 5,
                new CounterMap<>(YELLOW, 3),
                "Destroy all units.",
                owner);

        playable
                .addResolveEffect(game::destroyAllUnits);
    }
}
