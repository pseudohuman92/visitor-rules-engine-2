package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.Damage;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

public class RR04 extends Ritual {

    public RR04(Game game, UUID owner) {
        super(game, "RR04", 2,
                new CounterMap<>(RED, 1),
                "Deal 3 damage to a target.",
                owner);

        playable
                .setTargetSingleUnitOrPlayer(null, targetId ->
                        game.dealDamage(id, targetId, new Damage(3)), null
                );
    }
}
