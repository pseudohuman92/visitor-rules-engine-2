package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Trample;
import static com.visitor.game.parts.Base.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.GREEN;

public class GR03 extends Ritual {

    public GR03(Game game, UUID owner) {
        super(game, "GR03", 5,
                new CounterMap<>(GREEN, 3),
                "Units you control gains +3/+3 and trample until end of turn.",
                owner);

        playable
                .addResolveEffect(() ->
                        game.forEachInZone(controller, Play, Predicates::isUnit,
                                cardId -> {
                                    game.addTurnlyAttackAndHealth(cardId, 3, 3);
                                    game.addTurnlyCombatAbility(cardId, Trample);
                                })
                );
    }
}
