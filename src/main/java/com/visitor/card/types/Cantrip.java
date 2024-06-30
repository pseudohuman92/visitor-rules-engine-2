package com.visitor.card.types;

import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types.Knowledge;

import java.util.UUID;


/**
 * Abstract class for the Spell card type.
 *
 * @author pseudo
 */
public abstract class Cantrip extends Spell {

    public Cantrip(Game game, String name, int cost, CounterMap<Knowledge> knowledge, String text, UUID owner) {
        super(game, name, cost, knowledge, text, owner);

        subtypes.add(CardSubtype.Cantrip);
        playable.setFast().setEphemeral();
    }
}
