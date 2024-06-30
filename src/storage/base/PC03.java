package com.visitor.sets.base;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class PC03 extends Cantrip {
    public PC03(Game game, UUID owner) {
        super(game, "PC03", 2,
                new CounterMap<>(PURPLE, 2),
                "Deal 2 damage to target unit. You gain 2 life.",
                owner);

        playable
                .setTargetSingleUnit(null, null,
                        cardId -> game.dealDamage(id, cardId, 2),
                        () -> game.gainHealth(controller, 2)
                );
    }
}
