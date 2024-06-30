package com.visitor.sets.base2;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types;

import java.util.UUID;

public class FlameStrike extends Ritual {
    public FlameStrike(Game game, UUID owner) {
        super(game, "Flame Strike", 2,
                new CounterMap<>(Types.Knowledge.RED, 1),
                "Deal X damage to any target, where X is equal to {R} you have.",
                owner);

        playable.addTargetSingleUnitOrPlayer(Base.Zone.Play, t -> game.dealDamage(getId(), t, game.getKnowledgeCount(controller, Types.Knowledge.RED)), false);
    }
}
