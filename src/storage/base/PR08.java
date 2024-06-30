package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Discard_Pile;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class PR08 extends Ritual {

    public PR08(Game game, UUID owner) {
        super(game, "PR08", 5,
                new CounterMap<>(PURPLE, 1),
                "Resurrect a unit from your discard pile.",
                owner);

        playable
                .setTargetSingleUnit(Discard_Pile, null, game::resurrect, null);
    }
}
