package com.visitor.card.properties;

import com.visitor.game.Card;
import com.visitor.game.Player;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Predicates;
import com.visitor.protocol.Types;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TargetingEffect {

    private final Game game;
    private final Card card;
    private final UUID id;
    private final int minTargets;
    private final int maxTargets;
    private final Predicate<Targetable> predicate;
    private final String targetingMessage;
    private final Base.Zone zone;
    private Arraylist<UUID> targets;
    private final Consumer<UUID> targetsEffect;

    public TargetingEffect(
            Game game,
            Card card,
            Base.Zone zone,
            int minTargets,
            int maxTargets,
            Predicate<Targetable> predicate,
            String targetingMessage,
            Consumer<UUID> targetsEffect) {
        this.game = game;
        this.card = card;
        this.zone = Objects.requireNonNullElse(zone, Base.Zone.None);
        this.id = UUID.randomUUID();
        this.minTargets = minTargets;
        this.maxTargets = maxTargets;
        this.predicate = predicate != null ? predicate : Predicates::none;
        this.targetingMessage = targetingMessage != null ? targetingMessage : "";
        this.targetsEffect = targetsEffect;
        this.targets = new Arraylist<>();
    }

    public TargetingEffect(
            Game game,
            Card card,
            Runnable effect) {
        this(game, card, Base.Zone.Play, 0, 0, Predicates::none, "", i -> effect.run());
    }

    public int getMinTargets() {
        return minTargets;
    }

    public int getMaxTargets() {
        return maxTargets;
    }

    public String getTargetingMessage() {
        return targetingMessage;
    }

    public Arraylist<UUID> getAllPossibleTargets(){return new Arraylist<>(game.getAllFrom(card.controller, zone, predicate).transform(Targetable::getId)); }


    public Types.Targeting.Builder toTargetingBuilder() {
        return Types.Targeting.newBuilder()
                .setId(id.toString())
                .setMinTargets(minTargets)
                .setMaxTargets(maxTargets)
                .setTargetMessage(targetingMessage)
                .addAllPossibleTargets(getAllPossibleTargets().transformToStringList());
    }

    public Arraylist<UUID> getTargets() {
        return targets;
    }

    public void setTargets(Arraylist<UUID> targets) {
        this.targets = targets;
    }

    public UUID getId() {
        return id;
    }

    public void clear() {
        targets.clear();
    }

    public void runEffect(){
        if (maxTargets < 1) {
            targetsEffect.accept(UUID.randomUUID());
        } else {
            for (int i = 0; i < targets.size(); i++) {
                targetsEffect.accept(targets.get(i));
            }
        }
        targets.clear();
    }

    public Base.Zone getZone() {
        return zone;
    }

    public boolean hasEnoughTargets(){
        return game.hasInWithPlayers(card.controller, zone, predicate, minTargets);
    }
}
