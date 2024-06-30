package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class Banish extends Cantrip {
    public Banish(Game game, UUID owner) {
        super(game, "Banish", 3,
                new CounterMap<>(YELLOW, 2),
                "Cancel target card.",
                owner);

        playable.addTargetSingleCard(Base.Zone.Stack, Predicates::any, "Select a card to cancel", game::cancel, false);
    }
}
