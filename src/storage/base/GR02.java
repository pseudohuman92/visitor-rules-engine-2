package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Discard_Pile;
import static com.visitor.protocol.Types.Knowledge.GREEN;

public class GR02 extends Ritual {

    public GR02(Game game, UUID owner) {
        super(game, "GR02", 3,
                new CounterMap<>(GREEN, 1),
                "Return target card from your discard pile to your hand.",
                owner);

        playable
                .setTargetSingleCard(Discard_Pile, null,
                        "Select a card from your discard pile.",
                        game::returnToHand, null);
    }
}
