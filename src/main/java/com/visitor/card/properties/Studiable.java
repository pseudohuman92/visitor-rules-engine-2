package com.visitor.card.properties;

import com.visitor.game.Card;
import com.visitor.game.Player;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.function.Supplier;

public class Studiable {

    private final Card card;
    private final Game game;

    private boolean purging;

    private Supplier<Boolean> studyCondition;
    private TriConsumer<Player, Boolean, CounterMap<Types.Knowledge>> studyEffect;

    private TriConsumer<Player, Boolean, CounterMap<Types.Knowledge>> additionalStudyEffect;

    public Studiable(Game game, Card card, TriConsumer<Player, Boolean, CounterMap<Types.Knowledge>> additionalStudy, boolean purging) {
        this.card = card;
        this.game = game;
        this.additionalStudyEffect = additionalStudy;
        this.purging = purging;

        setDefaultStudyCondition();
        setDefaultStudy();
    }

    public Studiable(Game game, Card card, TriConsumer<Player, Boolean, CounterMap<Types.Knowledge>> additionalStudy) {
        this(game, card, additionalStudy, false);
    }

    public Studiable(Game game, Card card) {
        this(game, card, (a, b, c) -> {});
    }

    public final boolean canStudy() {
        return studyCondition.get();
    }

    /**
     * Called by the server when you choose to studyCard this card.
     * It increases player's maximum energy and adds knowledgePool.
     */
    public final void study(Player player, boolean regular, CounterMap<Types.Knowledge> knowledge) {
        if (knowledge == null){
            knowledge = new CounterMap<>();
            CounterMap<Types.Knowledge> finalKnowledge = knowledge;
            card.knowledge.forEach((k, i) -> finalKnowledge.add(k));
            studyEffect.accept(player, regular, finalKnowledge);
            additionalStudyEffect.accept(player, regular, finalKnowledge);
        } else {
            studyEffect.accept(player, regular, knowledge);
            additionalStudyEffect.accept(player, regular, knowledge);
        }
    }

    //Default Setters
    public void setDefaultStudyCondition() {
        studyCondition = () -> game.canStudy(card.controller) && card.zone == Base.Zone.Hand;
    }

    public void setDefaultStudy() {
        studyEffect = (player, regular, knowledge) -> {
            card.zone = null;
            if (!purging)
                player.deck.shuffleInto(card);
            player.addEnergy(1, false);
            player.addMaxEnergy(1);
            player.addKnowledge(knowledge);
            if (regular) {
                player.removeStudy(1);
            }
        };
    }

    public Studiable setAdditionalStudyEffect(TriConsumer<Player, Boolean, CounterMap<Types.Knowledge>> additionalStudyEffect) {
        this.additionalStudyEffect = additionalStudyEffect;
        return this;
    }
}
