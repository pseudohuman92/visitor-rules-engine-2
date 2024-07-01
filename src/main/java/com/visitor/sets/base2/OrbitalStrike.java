package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class OrbitalStrike extends Ritual {
    public OrbitalStrike(Game game, UUID owner) {
        super(game, "Orbital Strike", 4,
                new CounterMap<>(YELLOW, 2),
                "Destroy all units without shields. Remove all shields from units with shields.",
                owner);
                playable.addResolveEffect(() -> {
                    game.forEachInZone(owner, Base.Zone.Both_Play, Predicates::isUnit, id -> {
                        if (game.getCard(id).getShields() > 0)
                            game.getCard(id).removeShields(game.getCard(id).getShields());
                        else
                            game.destroy(id);
                    });
                });
    }
}
