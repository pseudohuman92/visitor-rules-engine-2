package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Both_Play;
import static com.visitor.game.parts.Base.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class PR01 extends Ritual {

    public PR01(Game game, UUID owner) {
        super(game, "PR01", 1,
                new CounterMap<>(PURPLE, 1),
                "As an additional cost to cast this spell, sacrifice a unit.\n" +
                        "Destroy target unit.",
                owner);

        playable
                .addPlayAdditionalConditions(() ->
                        game.hasIn(playable.card.controller, Play, Predicates::isUnit, 1)
                )
                .addBeforePlay(() -> {
                    //Sacrificed Unit
                    UUID sacrificedUnit = game.selectFromZone(playable.card.controller, Play, Predicates::isUnit, 1, 1, "").get(0);
                    targets.add(game.selectFromZone(playable.card.controller, Both_Play, Predicates::isUnit, 1, 1, "").get(0));
                    game.sacrifice(sacrificedUnit);
                })
                .addResolveEffect(() -> {
                    if (game.isIn(controller, Both_Play, targets.get(0)))
                        game.destroy(targets.get(0));
                });
    }
}
