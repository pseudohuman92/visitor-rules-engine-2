package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class ProtectiveField extends Cantrip {
    public ProtectiveField(Game game, UUID owner) {
        super(game, "Protective Field", 1,
                new CounterMap<>(YELLOW, 1),
                "Target unit gains shield 1 until end of turn.\n{Y}{Y} - Gains shield 1 instead.",
                owner);

        playable.addTargetSingleUnit(null, Predicates::any, t -> {
            if (game.hasKnowledge(controller, new CounterMap<>(YELLOW, 2))){
                game.addShield(t, 1, true);
            } else {
                game.addShield(t, 1, false);
            }
        }, null, false);
    }
}
