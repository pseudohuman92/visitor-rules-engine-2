package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.*;

public class Strengthen extends Cantrip {
    public Strengthen(Game game, UUID owner) {
        super(game, "Strengthen", 1,
                new CounterMap<>(GREEN, 1),
                "Target unit gains +1/+1.\n{G}{G}{G} - Gains +2/+2 instead.",
                owner);

        playable.addTargetSingleUnit(null, Predicates::any, t -> {
            if (game.hasKnowledge(controller, new CounterMap<>(GREEN, 3))){
                game.addAttackAndHealth(t, 2,2, false);
            } else {
                game.addAttackAndHealth(t, 1, 1, false);
            }
        }, null, false);
    }
}
