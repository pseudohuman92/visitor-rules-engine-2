package com.visitor.sets.base;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

public class RC02 extends Cantrip {
    public RC02(Game game, UUID owner) {
        super(game, "RC02", 2,
                new CounterMap<>(RED, 1),
                "Target unit gets +3/+0 and gains first strike until end of turn.",
                owner);

        playable
                .setTargetSingleUnit(null, null, cardId -> {
                    game.addTurnlyAttackAndHealth(cardId, 3, 0);
                    game.addTurnlyCombatAbility(cardId, Combat.CombatAbility.FirstStrike);
                }, null);
    }
}
