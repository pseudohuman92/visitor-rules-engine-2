package com.visitor.game;

import com.visitor.card.properties.*;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.HelperFunctions;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.helpers.containers.Damage;
import com.visitor.protocol.Types;
import com.visitor.protocol.Types.Counter;
import com.visitor.protocol.Types.CounterGroup;
import com.visitor.protocol.Types.Knowledge;
import com.visitor.protocol.Types.KnowledgeGroup;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.visitor.game.parts.Base.Zone.*;
import static java.util.UUID.randomUUID;

/**
 * @author pseudo
 */
public abstract class Card implements Targetable {

    protected final Game game;
    private UUID id;
    public String name;
    public String text;
    public Arraylist<CardType> types;
    public Arraylist<CardSubtype> subtypes;
    public CounterMap<Knowledge> knowledge;

    public UUID controller;
    public Game.Zone zone;
    protected boolean depleted;
    protected CounterMap<Counter> counters;
    public Arraylist<UUID> attachments;

    protected Activatable activatable;
    protected Triggering triggering;
    public Combat combat;
    protected Playable playable;
    protected Studiable studiable;
    protected Attachable attachable;

    protected Arraylist<Runnable> enterPlayEffects;
    protected Arraylist<Runnable> leavePlayEffects;

    public Card(Game game, String name,
                CounterMap<Knowledge> knowledge,
                CardType type, String text, UUID controller) {
        this.game = game;
        id = randomUUID();
        counters = new CounterMap<>();
        types = new Arraylist<>(type);
        subtypes = new Arraylist<>();
        attachments = new Arraylist<>();
        enterPlayEffects = new Arraylist<>();
        leavePlayEffects = new Arraylist<>();

        this.name = name;
        if (knowledge != null)
            this.knowledge = knowledge;
        else
            this.knowledge = new CounterMap<>();
        this.text = text;
        this.controller = controller;
        this.depleted = false;
    }

    public void addCounters(Counter name, int count) {
        counters.add(name, count);
    }

    public void removeCounters(Counter name, int count) {
        counters.decrease(name, count);
    }

    public boolean hasCounters(Counter name, int i) {
        return counters.getOrDefault(name, 0) >= i;
    }

    public void deplete() {
        depleted = true;
    }

    public void newTurn() {
        depleted = false;
        if (combat != null)
            combat.newTurn();
    }

    /**
     * Function that clears status flags and supplementary data of the card.
     * Gets called from Card.leavePlay() and Game.cancel()
     */
    public void clear() {
        depleted = false;
        playable.clear();
        activatable.clear();
        attachments.clear();
        if (combat != null)
            combat.clear();
        if (attachable != null)
            attachable.clear();
    }

    /**
     * Gets added to Playable.resolvePlaceCard if Playable.resolveZone is Play.
     */
    public void enterPlay() {
        if (triggering != null) {
            triggering.register();
        }
        if (attachable != null) {
            attachable.attach();
        }
        enterPlayEffects.forEachInOrder(Runnable::run);
        game.addEvent(Event.enterPlay(this), true);
    }

    /**
     * Gets called by Card.moveToZone() if old Zone is play and new zone is not Play
     */
    public void leavePlay() {
        if (triggering != null) {
            triggering.deregister();
        }
        if (attachable != null) {
            attachable.removeFromAttached();
        }
        attachments.forEach(game::destroy);
        clear();
        leavePlayEffects.forEachInOrder(Runnable::run);
    }


    public void moveToZone(Game.Zone zone) {
        if (this.zone == Play && zone != Play) {
            leavePlay();
        }
        game.extractCard(id);
        game.putTo(controller, this, zone);
    }

    public void destroy() {
        moveToZone(Discard_Pile);
    }

    public void sacrifice() {
        moveToZone(Discard_Pile);
    }

    public void returnToHand() {
        moveToZone(Hand);
    }


	/*
	public void copyPropertiesFrom (Card c) {

		id = c.id;
		owner = c.owner;
		controller = c.controller;
		counters = c.counters;
		depleted = c.depleted;
		targets = c.targets;
	}
	*/

    private void runIfNotNull(Object object, Runnable runnable) {
        HelperFunctions.runIfNotNull(object, runnable, () ->
                System.out.println(toCardMessage().toString())
        );
    }

    private <T> T runIfNotNull(Object object, Supplier<T> supplier) {
        return HelperFunctions.runIfNotNull(object, supplier, () -> {
            System.out.println(toCardMessage().toString());
            return null;
        });
    }

    public final void play(boolean withCost) {
        runIfNotNull(playable, () -> playable.play(withCost));
    }

    public final void study(Player player, boolean regular, CounterMap<Types.Knowledge> knowledge) {
        runIfNotNull(studiable, () -> studiable.study(player, regular, knowledge));
    }

    public final void checkEvent(Event e) {
        runIfNotNull(triggering, () -> triggering.checkEvent(e));
    }

    public final void resolve() {
        runIfNotNull(playable, () -> playable.resolve());
    }

    public final void receiveDamage(Damage damage, Card source) {
        runIfNotNull(combat, () -> combat.receiveDamage(damage, source));
    }

    public void heal(int healAmount) {
        runIfNotNull(combat, () -> combat.heal(healAmount));
    }

    public final int getAttack() {
        return runIfNotNull(combat, () -> combat.getAttack());
    }

    public void addAttackAndHealth(int attack, int health, boolean turnly) {
        runIfNotNull(combat, () -> combat.addAttackAndHealth(attack, health, turnly));
    }

    //TODO: Refactor these like above
    public final void unsetAttacking() {
        runIfNotNull(combat, combat::unsetAttacking);
    }

    public final void addBlocker(UUID blockerId) {
        runIfNotNull(combat, () -> combat.addBlocker(blockerId));
    }

    public final void setBlocking(UUID blockedBy) {
        runIfNotNull(combat, () -> combat.setBlocking(blockedBy));
    }

    public final void dealAttackDamage(boolean firstStrike) {
        runIfNotNull(combat, () -> combat.dealAttackDamage(firstStrike));
    }

    public final void dealBlockDamage() {
        runIfNotNull(combat, combat::dealBlockDamage);
    }

    public final void activate(UUID abilityId) {
        runIfNotNull(activatable, () -> activatable.activate(abilityId));
    }

    public final void unsetBlocking() {
        runIfNotNull(combat, combat::unsetBlocking);
    }

    public boolean canDieFromBlock() {
        return runIfNotNull(combat, combat::canDieFromBlock);
    }

    public boolean canDieFromAttack() {
        return runIfNotNull(combat, combat::canDieFromAttack);
    }

    public void maybeDieFromBlock() {
        if (canDieFromBlock()) {
            game.destroy(id);
        }
    }

    public void maybeDieFromAttack() {
        if (canDieFromAttack()) {
            game.destroy(id);
        }
    }

    public void addCombatAbility(Combat.CombatAbility ability) {
        runIfNotNull(combat, () -> combat.addCombatAbility(ability));
    }

    public void addCombatAbility(Combat.CombatAbility ability, int count) {
        runIfNotNull(combat, () -> combat.addCombatAbility(ability, count));
    }

    public void removeCombatAbility(Combat.CombatAbility ability) {
        runIfNotNull(combat, () -> combat.removeCombatAbility(ability));
    }

    public final boolean canAttack() {
        return combat != null && combat.canAttack();
    }

    public final boolean canBlock() {
        return combat != null && combat.canBlock();
    }

    public final boolean canBlock(Card c) {
        return combat != null && combat.canBlock(c);
    }

    public final boolean canPlay(boolean withCost) {
        return playable != null && playable.canPlay(withCost);
    }

    public final boolean canActivate() {
        return activatable != null && activatable.canActivate();
    }

    public final boolean canStudy() {
        return studiable != null && studiable.canStudy();
    }

    public final boolean isAttacking() {
        return combat != null && combat.isAttacking();
    }

    public final void setAttacking(UUID attacker) {
        runIfNotNull(combat, () -> combat.setAttacking(attacker));
    }

    public final boolean hasCombatAbility(Combat.CombatAbility combatAbility) {
        return combat != null && combat.hasCombatAbility(combatAbility);
    }

    public boolean hasType(CardType type) {
        return types.contains(type);
    }

    public boolean hasSubtype(CardSubtype type) {
        return subtypes.contains(type);
    }

    public void endTurn() {
        runIfNotNull(combat, () -> combat.endTurn());
        runIfNotNull(activatable, () -> activatable.endTurn());
    }

    public void addTurnlyCombatAbility(Combat.CombatAbility combatAbility) {
        runIfNotNull(combat, () -> combat.addTurnlyCombatAbility(combatAbility));
    }

    public void addTurnlyActivatedAbility(ActivatedAbility ability) {
        runIfNotNull(activatable, () -> activatable.addTurnlyActivatedAbility(ability));
    }


    public void addShield(int i, boolean turnly) {
        runIfNotNull(combat, () -> combat.addShield(i, turnly));
    }

    public void setAttack(int i) {
        runIfNotNull(combat, () -> combat.setAttack(i));
    }

    public void setHealth(int i) {
        runIfNotNull(combat, () -> combat.setHealth(i));
    }


    public boolean isDamagable() {
        return combat != null;
    }

    public boolean isStudiable() {
        return studiable != null;
    }

    public boolean hasColor(Knowledge knowledge) {
        return this.knowledge.contains(knowledge);
    }

    public boolean isDeploying() {
        return combat != null && combat.isDeploying();
    }

    public boolean isDepleted() {
        return depleted;
    }


    public int getHealth() {
        return runIfNotNull(combat, () -> combat.getHealth());
    }

    public boolean isColorless() {
        return knowledge.isEmpty();
    }

    /**
     * Enter Play TargetingEffect setters
     */


    public void addEnterPlayEffect(CounterMap<Types.Knowledge> knowledge, Runnable effect) {
        if (knowledge == null) {
            knowledge = new CounterMap<>();
        }
        CounterMap<Types.Knowledge> finalKnowledge = knowledge;
        enterPlayEffects.add(() -> game.runIfHasKnowledge(controller, finalKnowledge, effect));
    }

    public void addEnterPlayEffectOnStack(CounterMap<Types.Knowledge> knowledge, String text, Runnable effect) {
        addEnterPlayEffect(knowledge, () -> game.addToStack(new AbilityCard(game, this, text, effect)));
    }

    public void addLeavePlayEffect(CounterMap<Types.Knowledge> knowledge, Runnable effect) {
        if (knowledge == null) {
            knowledge = new CounterMap<>();
        }
        CounterMap<Types.Knowledge> finalKnowledge = knowledge;
        leavePlayEffects.add(() -> game.runIfHasKnowledge(controller, finalKnowledge, effect));
    }

    public void addLeavePlayEffectOnStack(CounterMap<Types.Knowledge> knowledge, String text, Runnable effect) {
        addLeavePlayEffect(knowledge, () -> game.addToStack(new AbilityCard(game, this, text, effect)));
    }

    public boolean isPlayable() {
        return playable != null;
    }

    public int getCost() {
        return playable.getCost();
    }


    public void setDonating() {
        addEnterPlayEffect(null, () -> game.donate(id, game.getOpponentId(controller), Play));
    }

    public void addAttachment(UUID attachmentId) {
        attachments.add(attachmentId);
    }

    public void removeAttachment(UUID attachmentId) {
        attachments.remove(attachmentId);
    }

    public void setAttachable(Predicate<Targetable> validTarget, Consumer<UUID> afterAttachEffect, Consumer<UUID> afterRemoveEffect) {
        attachable = new Attachable(game, this, validTarget, afterAttachEffect, afterRemoveEffect);
        playable.addTargetSingleCard(Game.Zone.Both_Play, validTarget, "Choose a target to attach", attachable::setAttachTo, false);
    }

    public void loseAttack(int attack) {
        runIfNotNull(combat, () -> combat.loseAttack(attack));
    }

    //Checks death after life loss
    public void loseHealth(int health) {
        runIfNotNull(combat, () -> {
            combat.loseHealth(health);
            if (combat.getHealth() == 0)
                game.destroy(id);
        });
    }

    public Types.CardP.Builder toCardMessage() {
        String packageName = this.getClass().getPackageName();
        String set = packageName.substring(packageName.lastIndexOf(".") + 1);
        Types.CardP.Builder builder = Types.CardP.newBuilder()
                .setId(id.toString())
                .setSet(set)
                .setName(name)
                .setDepleted(depleted)
                .setDescription(text)
                .setLoyalty(-1)
                .setCanPlay(canPlay(true))
                .setCanStudy(canStudy())
                .setCanActivate(canActivate())
                .setCanAttack(canAttack())
                .setCanBlock(canBlock())
                .addAllTypes(types.transformToStringList())
                .addAllSubtypes(subtypes.transformToStringList())
                .addAllAttachments(attachments.transformToStringList())
                .addAllTargets(getAllTargets().transformToStringList());

        counters.forEach((k, i) -> builder.addCounters(CounterGroup.newBuilder()
                .setCounter(k)
                .setCount(i).build()));

        knowledge.forEach((k, i) -> builder.addKnowledgeCost(KnowledgeGroup.newBuilder()
                .setKnowledge(k)
                .setCount(i).build()));

        if (playable != null) {
            builder.setCost(playable.getCost() + "");
            if (playable.needsTargets()){
                builder.addAllPlayTargets(playable.getTargetingBuilder());
            }

        }
        if (activatable != null){
            builder.addAllActivateTargets(activatable.getAbilityBuilders());
        }

        if (combat != null) {
            builder.setCombat(combat.toCombatMessage());
        }

        return builder;
    }

    public void setDisappearing() {
        playable.setDisappearing();
    }

    public int drain(int amount) {
        return combat.drain(amount);
    }

    public int getMaxHealth() {
        return combat.getMaxHealth();
    }

    public void setPlayableTargets(Arraylist<Types.TargetSelection> targets) {
        playable.setTargets(targets);
    }

    public void setActivatableTargets(UUID abilityId, Arraylist<Types.TargetSelection> targets) {
        activatable.setAbilityTargets(abilityId, targets);
    }

    @Override
    public UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
    }

    @Override
    public Game.Zone getZone() {
        return zone;
    }

    public Arraylist<UUID> getAllTargets(){
        Arraylist<UUID> a = new Arraylist<>();
        if (playable != null && playable.needsTargets()) {
            a.addAll(playable.getAllTargets());
        }
        if (activatable != null){
            a.addAll(activatable.getAllTargets());
        }
        return a;
    };

    public int getShields() {
        if (combat != null){
            return combat.getShield();
        } else {
            return 0;
        }
    }

    public void removeShields(int shieldAmount) {
        runIfNotNull(combat, () -> combat.removeShield(shieldAmount));
    }

    public CounterMap<Combat.CombatAbility> getCombatAbilities() {
        if (combat != null){
            return combat.getCombatAbilities();
        } else {
            return new CounterMap<>();
        }
    }

    public enum CardType {
        Ally,
        Asset,
        Junk,
        Passive,
        Spell,
        Tome,
        Unit,
        Attachment,

        // Internal types
        Ability,
        Effect
    }

    public enum CardSubtype {
        // Spell subtypes
        Cantrip,
        Ritual,

        // Unit Subtypes
        Zombie,
        Wizard,
        Bat,
        Spirit,
        Warrior,
        Golem,
        Elf,
        Insect, Plant, Wurm, Wolf, Drone;
    }

}