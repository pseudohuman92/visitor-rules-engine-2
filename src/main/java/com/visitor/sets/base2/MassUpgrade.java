package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class MassUpgrade extends Ritual {
    public MassUpgrade(Game game, UUID owner) {
        super(game, "Mass Upgrade", 2,
                new CounterMap<>(YELLOW, 1),
                "Each unit you control gains +1/+1",
                owner);

        playable.addResolveEffect(() -> {
            game.forEachInZone(controller, Base.Zone.Play, Predicates::isUnit, c -> game.addAttackAndHealth(c, 1, 1, false));
        });
    }
}
