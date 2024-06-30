package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.helpers.Predicates.not;
import static com.visitor.protocol.Types.Knowledge.BLUE;

public class BC04 extends Cantrip {
    public BC04(Game game, UUID owner) {
        super(game, "BC04", 2,
                new CounterMap<>(BLUE, 1),
                "Cancel target non-unit card.",
                owner);

        playable
                .setTargetSingleCard(Game.Zone.Stack, not(Predicates::isUnit), "Select a non-unit card.", game::cancel, null);
    }
}
