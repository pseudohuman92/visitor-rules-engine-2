package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.Damage;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.protocol.Types.Knowledge.RED;

public class RR01 extends Ritual {

    public RR01(Game game, UUID owner) {
        super(game, "RR01", 1,
                new CounterMap<>(RED, 1),
                "Deal 3 damage to target unit with flying.",
                owner);

        playable
                .setTargetSingleUnit(null, c -> c.hasCombatAbility(Flying), cardId -> game.dealDamage(this.id, cardId, new Damage(3)), null);
    }
}
