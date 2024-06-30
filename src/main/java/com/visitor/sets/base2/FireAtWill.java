package com.visitor.sets.base2;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

public class FireAtWill extends Ritual {
    public FireAtWill(Game game, UUID owner) {
        super(game, "Fire At Will", 2,
                new CounterMap<>(RED, 1),
                "Deal 1 damage to your opponent for each unit you control.",
                owner);

        playable.addResolveEffect(() -> {
            for (int i = 0; i < game.countInZone(controller, Base.Zone.Play, Predicates::isUnit); i++) {
                game.dealDamage(getId(), game.getOpponentId(controller), 1);
            }
        });
    }
}
