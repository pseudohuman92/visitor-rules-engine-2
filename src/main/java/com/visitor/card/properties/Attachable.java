/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.card.properties;

import com.visitor.game.Card;
import com.visitor.game.parts.Game;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Interface for cards that has an activating ability.
 *
 * @author pseudo
 */
public class Attachable {

    private final Card card;
    private final Game game;

    private final Predicate<Targetable> validTarget;
    private UUID attachedTo;
    private final Consumer<UUID> afterAttached;
    private final Consumer<UUID> afterRemoved;

    // Constructors
    public Attachable(Game game, Card card, Predicate<Targetable> validTarget,
                      Consumer<UUID> afterAttached, Consumer<UUID> afterRemoved) {
        this.game = game;
        this.card = card;
        this.validTarget = validTarget;
        this.afterAttached = afterAttached;
        this.afterRemoved = afterRemoved;
    }

    public final void setAttachTo(UUID attachedId) {
        attachedTo = attachedId;
    }

    /**
     * Gets automatically called from Card's enterPlay().
     */
    public final void attach() {
        game.addAttachmentTo(attachedTo, card.getId());
        afterAttached.accept(attachedTo);
    }

    /**
     * Gets automatically called from Card's leavePlay().
     */
    public final void removeFromAttached() {
        game.removeAttachmentFrom(attachedTo, card.getId());
        afterRemoved.accept(attachedTo);
        attachedTo = null;
    }


    public void clear() {
        attachedTo = null;
    }
}
