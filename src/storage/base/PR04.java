package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Discard_Pile;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class PR04 extends Ritual {

    public PR04(Game game, UUID owner) {
        super(game, "PR04", 2,
                new CounterMap<>(PURPLE, 1),
                "Restore and return up to 2 target unit cards from your discard pile to your hand, \n" +
                        "then discard 1 card.",
                owner);

        playable
                .setTargetMultipleUnits(Discard_Pile, 0, 2,
                        cardId -> game.restore(cardId).returnToHand(),
                        () -> game.discard(controller, 1)
                );


    }
}
