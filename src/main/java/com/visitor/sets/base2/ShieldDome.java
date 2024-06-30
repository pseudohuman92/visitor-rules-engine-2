package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class ShieldDome extends Cantrip {
    public ShieldDome(Game game, UUID owner) {
        super(game, "Shield Dome", 3,
                new CounterMap<>(YELLOW, 2),
                "You and each unit you control gains 2 shield until end of turn.",
                owner);

        playable.addResolveEffect(() -> {
            game.forEachInZone(controller, Base.Zone.Play, Predicates::isUnit, c -> game.addShield(c, 2, true));
            game.addShield(controller, 2, true);
        });
    }
}
