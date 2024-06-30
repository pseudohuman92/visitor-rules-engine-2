package com.visitor.sets.test;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Predicates;

import java.util.UUID;

public class MultiTargetCantrip extends Cantrip {
    public MultiTargetCantrip(Game game, UUID owner) {
        super(game, "Multi Target Cantrip", 0, null,
                "Discard up to two cards",
                owner);

        playable.addTargetMultipleCards(Base.Zone.Hand, Predicates.anotherCard(getId()), 0, 2,
                "Select up to two cards to discard.", c -> game.discard(controller, c), false);
    }
}
