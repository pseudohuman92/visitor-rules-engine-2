package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.Damage;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

public class RC04 extends Cantrip {
    public RC04(Game game, UUID owner) {
        super(game, "RC04", 4,
                new CounterMap<>(RED, 2),
                "Deal 4 damage to target unit and 2 damage to its controller.",
                owner);

        playable
                .setTargetSingleUnit(null, null, cardId -> {
                    game.dealDamage(id, cardId, new Damage(4));
                    game.dealDamage(id, game.getCard(cardId).controller, new Damage(2));
                }, null);
    }
}
