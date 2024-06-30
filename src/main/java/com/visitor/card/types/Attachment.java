package com.visitor.card.types;

import com.visitor.card.properties.*;
import com.visitor.game.Card;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types.Knowledge;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Abstract class for the Asset card type.
 *
 * @author pseudo
 */
public abstract class Attachment extends Card {

    public Attachment(Game game, String name, int cost, CounterMap<Knowledge> knowledge,
                      String text, UUID owner,
                      Predicate<Targetable> validTarget, Consumer<UUID> afterAttachEffect,
                      Consumer<UUID> afterRemoveEffect) {
        super(game, name, knowledge, CardType.Attachment, text, owner);

        playable = new Playable(game, this, cost).setSlow().setPersistent();
        studiable = new Studiable(game, this);
        activatable = new Activatable(game, this);
        triggering = new Triggering(game, this);

        setAttachable(validTarget, afterAttachEffect, afterRemoveEffect);
    }


}
