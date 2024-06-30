package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.Damage;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

public class RC03 extends Cantrip {
    public RC03(Game game, UUID owner) {
        super(game, "RC03", 3,
                new CounterMap<>(RED, 1),
                "Deal 2 damage to each unit.",
                owner);

        playable
                .addResolveEffect(() ->
                        game.forEachInZone(controller, Game.Zone.Both_Play,
                                Predicates::isUnit, cardId -> game.dealDamage(id, cardId, new Damage(2))));
    }
}
