package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;

public class BC03 extends Cantrip {
    public BC03(Game game, UUID owner) {
        super(game, "BC03", 2,
                new CounterMap<>(BLUE, 1),
                "Cancel target unit card.",
                owner);

        playable
                .setTargetSingleUnit(Game.Zone.Stack, null, game::cancel, null);
    }
}
