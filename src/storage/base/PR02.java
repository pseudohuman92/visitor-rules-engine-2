package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Discard_Pile;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class PR02 extends Ritual {

    public PR02(Game game, UUID owner) {
        super(game, "PR02", 1,
                new CounterMap<>(PURPLE, 1),
                "Restore and return target unit card from your discard pile to your hand.",
                owner);

        playable
                .setTargetSingleUnit(Discard_Pile, null, cardId -> game.restore(cardId).returnToHand(), null);
    }
}
