package com.visitor.card.types;

import com.visitor.card.properties.Playable;
import com.visitor.card.properties.Studiable;
import com.visitor.game.Card;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types.Knowledge;

import java.util.UUID;


/**
 * Abstract class for the Spell card type.
 *
 * @author pseudo
 */
public abstract class Spell extends Card {

    public Spell(Game game, String name, int cost, CounterMap<Knowledge> knowledge, String text, UUID owner) {
        super(game, name, knowledge, CardType.Spell, text, owner);

        playable = new Playable(game, this, cost);
        studiable = new Studiable(game, this);
    }
}
