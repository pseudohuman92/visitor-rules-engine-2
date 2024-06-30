package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class Prohibit extends Cantrip {
    public Prohibit(Game game, UUID owner) {
        super(game, "Prohibit", 2,
                new CounterMap<>(YELLOW, 3),
                "Cancel target card.",
                owner);

        playable.addTargetSingleCard(Base.Zone.Stack, Predicates::any, "Select a card to cancel", game::cancel, false);
    }
}
