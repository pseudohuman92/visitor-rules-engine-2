package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class YR02 extends Ritual {

    public YR02(Game game, UUID owner) {
        super(game, "YR02", 2,
                new CounterMap<>(YELLOW, 1),
                "Put target unit with attack >= 4 to bottom of its controller's deck.",
                owner);

        playable
                .setTargetSingleUnit(null, c -> c.getAttack() >= 4, game::putToBottomOfDeck, null);
    }
}
