package com.visitor.sets.base2;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class ASoulforASoul extends Ritual {
    public ASoulforASoul(Game game, UUID owner) {
        super(game, "A Soul for A Soul", 1,
                new CounterMap<>(PURPLE, 1),
                "Additional Cost - Sacrifice a unit.\nDestroy target unit.",
                owner);

        playable.addTargetSingleUnit(Base.Zone.Play, Predicates.controlledBy(controller),
                game::sacrifice, "Select unit to sacrifice", true);

        playable.addTargetSingleUnit(Base.Zone.Both_Play, null,
                c -> game.destroy(getId(), c), "Select unit to destroy", false);
    }
}
