package com.visitor.sets.base2;

import com.visitor.card.types.Ritual;
import com.visitor.game.Card;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class Disassemble extends Ritual {

    public Disassemble(Game game, UUID owner) {
        super(game, "Disassemble", 1,
                new CounterMap<>(YELLOW, 1),
                "Return target card to its controller's hand.",
                owner);

        playable.addTargetSingleCard(Base.Zone.Both_Play, Predicates::any, "Select a card to return.",
                game::returnToHand, false);
    }
}
