package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

public class NuclearBlast extends Ritual {
    public NuclearBlast(Game game, UUID owner) {
        super(game, "Nuclear Blast", 6,
                new CounterMap<>(RED, 4),
                "Deal 10 damage to everything",
                owner);

        playable.addResolveEffect(() -> {
                game.getAllFrom(Base.Zone.Play, Predicates::isUnit).forEach(
                t -> game.dealDamage(getId(), t.getId(), 10));
                game.getAllPlayerIds().forEach(i -> game.dealDamage(getId(), i, 10));
        });
    }
}
