package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class SiphonLife extends Cantrip {
    public SiphonLife(Game game, UUID owner) {
        super(game, "Siphon Life", 1,
                new CounterMap<>(PURPLE, 1),
                "Drain 2 from target unit.\n{P}{P}{P} - Drain 3 instead.",
                owner);

        playable.addTargetSingleUnit(null, Predicates::any, t -> {
            if (game.hasKnowledge(controller, new CounterMap<>(PURPLE, 3))){
                game.drain(getId(), t, 3);
            } else {
                game.drain(getId(), t, 2);
            }
        }, "", false);
    }
}
