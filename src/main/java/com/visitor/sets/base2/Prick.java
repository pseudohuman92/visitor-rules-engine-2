package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;

import java.util.UUID;

public class Prick extends Cantrip {
    public Prick(Game game, UUID owner) {
        super(game, "Prick", 0,
                null,
                "Deal 1 damage to any target",
                owner);

        playable.addTargetSingleUnitOrPlayer(Base.Zone.Play, t -> game.dealDamage(getId(), t, 1), false);
    }
}
