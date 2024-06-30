package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;


public class InfuseLife extends Cantrip {
    int x = 0;
    public InfuseLife(Game game, UUID owner) {
        super(game, "Infuse Life", 2,
                new CounterMap<>(PURPLE, 1),
                "Additional Cost - Sacrifice a unit.\nUnits you control gains +X/+X where X is sacrificed unit's health.",
                owner);

        playable.addTargetSingleUnit(Base.Zone.Play, Predicates.controlledBy(controller),
                c -> {
                    x = game.getCard(c).getHealth();
                    game.sacrifice(c);
                }, "Select unit to sacrifice", true);

        playable.addResolveEffect(() -> game.forEachInZone(controller, Base.Zone.Play, Predicates::isUnit, c -> game.addAttackAndHealth(c, x, x, false)));
    }
}
