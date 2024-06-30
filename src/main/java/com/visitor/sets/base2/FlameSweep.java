package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class FlameSweep extends Ritual {
    public FlameSweep(Game game, UUID owner) {
        super(game, "Flame Sweep", 3,
                new CounterMap<>(RED, 2),
                "Deal 3 damage to each unit.",
                owner);

        playable.addResolveEffect(()->{
            game.forEachInZone(null, Base.Zone.Both_Play, Predicates::isUnit, i -> game.dealDamage(getId(), i, 3));
        });
    }
}
