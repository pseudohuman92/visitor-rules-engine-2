package com.visitor.sets.test;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Predicates;

import java.util.UUID;

public class MultiSelectionCantrip extends Cantrip {
    public MultiSelectionCantrip(Game game, UUID owner) {
        super(game, "Multi Target Cantrip", 0, null,
                "Deal 1 damage to a target. Deal 1 damage to a target.",
                owner);

        playable.addTargetSingleUnitOrPlayer(Base.Zone.Both_Play, c -> game.dealDamage(getId(), c, 1), false);
        playable.addTargetSingleUnitOrPlayer(Base.Zone.Both_Play, c -> game.dealDamage(getId(), c, 1), false);
    }
}
