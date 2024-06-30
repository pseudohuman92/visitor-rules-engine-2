package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.Card;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class YR03 extends Ritual {

    public YR03(Game game, UUID owner) {
        super(game, "YR03", 2,
                new CounterMap<>(YELLOW, 1),
                "Destroy target depleted unit",
                owner);

        playable
                .setTargetSingleUnit(null, Card::isDepleted, cardId -> game.destroy(id, cardId), null);
    }
}
