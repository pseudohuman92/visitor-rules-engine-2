package com.visitor.sets.test;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;

import java.util.UUID;

public class SingleTargetCantrip extends Cantrip {
    public SingleTargetCantrip(Game game, UUID owner) {
        super(game, "Single Target Cantrip", 0, null,
                "Return target unit to its controller's hands.",
                owner);

        playable.addTargetSingleUnit(null, null, game::returnToHand, null, false);
    }
}
