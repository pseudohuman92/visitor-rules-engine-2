package com.visitor.sets.base;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class YC01 extends Cantrip {
    public YC01(Game game, UUID owner) {
        super(game, "YC01", 2,
                new CounterMap<>(YELLOW, 1),
                "Target unit gets +2/+2 and lifelink until end of turn.",
                owner);

        playable
                .setTargetSingleUnit(null, null, cardId -> {
                    game.addTurnlyAttackAndHealth(cardId, 2, 2);
                    game.addTurnlyCombatAbility(cardId, Combat.CombatAbility.Lifelink);
                }, null);
    }
}
