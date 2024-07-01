package com.visitor.card.properties;

import com.visitor.game.Card;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.Damage;
import com.visitor.protocol.Types;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.visitor.card.properties.Combat.CombatAbility.*;

public class Combat {

    private final Card card;
    private final Game game;

    private int attack;
    private int health;
    private int maxHealth;
    private int shield;
    private boolean deploying;
    private UUID blockedAttacker;
    private UUID blockedBy;
    private UUID attackTarget;
    private final CounterMap<CombatAbility> combatAbilityList;

    private int turnlyAttack;
    private int turnlyHealth;
    private int turnlyShield;
    private final CounterMap<CombatAbility> turnlyCombatAbilityList;

    private final Supplier<Boolean> canAttackAdditional;
    private Supplier<Boolean> canAttack;
    private final Predicate<Card> canBlockAdditional;
    private final Predicate<Card> canBlock;
    private Supplier<Boolean> canBlockGeneral;
    private final Consumer<UUID> setAttacking;
    private final Runnable unsetAttacking;
    private final Consumer<UUID> setBlocking;
    private final Runnable unsetBlocking;
    private final Consumer<UUID> setBlocker;
    private final Runnable newTurn;
    private final Consumer<Boolean> dealAttackDamage;
    private final Runnable dealBlockDamage;
    private final BiConsumer<Damage, Card> receiveDamage;
    private final Arraylist<BiConsumer<UUID, Damage>> damageEffects;

    public Combat(Game game, Card card, int attack, int health) {
        this.card = card;
        this.game = game;
        this.attack = attack;
        this.health = health;
        this.maxHealth = health;
        combatAbilityList = new CounterMap<>();
        turnlyCombatAbilityList = new CounterMap<>();
        damageEffects = new Arraylist<>();
        //Default implementations
        canAttackAdditional = () -> true;

        canAttack = () ->
                !card.isDepleted() &&
                        (!deploying || hasCombatAbility(CombatAbility.Blitz)) &&
                        !hasCombatAbility(Defender) &&
                        card.zone == Base.Zone.Play &&
                        canAttackAdditional.get();

        canBlockAdditional = (unit) -> true;

        canBlock = (unit) ->
                !card.isDepleted() &&
                        !unit.hasCombatAbility(Unblockable) &&
                        (!unit.hasCombatAbility(Evasive) || hasCombatAbility(Evasive) || hasCombatAbility(Reach)) &&
                        canBlockAdditional.test(unit);

        canBlockGeneral = () -> !card.isDepleted();

        setAttacking = (target) -> {
            if (!hasCombatAbility(Vigilance))
                card.deplete();
            attackTarget = target;
        };

        unsetAttacking = () -> {
            attackTarget = null;
            blockedBy = null;
        };

        setBlocking = (unit) -> blockedAttacker = unit;

        unsetBlocking = () -> blockedAttacker = null;

        setBlocker = (blocker) -> blockedBy = blocker;

        newTurn = () -> {
            if (hasCombatAbility(Regenerate)) {
                game.heal(card.getId(), combatAbilityList.get(Regenerate));
            }
            if (hasCombatAbility(Decay)) {
                loseMaxHealth(1);
                loseHealth(1);
                loseAttack(1);
            }
            deploying = false;
        };

        dealAttackDamage = (firstStrike) -> {
            UUID id = card.getId();
            if (blockedBy == null) {
                game.dealDamage(id, attackTarget, new Damage(getAttack(), firstStrike, true));
            } else {
                game.dealDamage(id, blockedBy, new Damage(getAttack(), firstStrike, true));
            }
        };

        dealBlockDamage = () -> {
            UUID id = card.getId();
            if (blockedAttacker != null)
                game.dealDamage(id, blockedAttacker, new Damage(getAttack(), false, true));
        };

        receiveDamage = (damage, source) -> {
            int damageAmount = damage.amount - (shield + turnlyShield);

            //Apply shields
            if (damageAmount <= 0) {
                return;
            }

            int dealtDamageAmount = damageAmount;

            if (source.isAttacking() &&
                source.hasCombatAbility(Trample) &&
                damageAmount > getHealth()) {

                int leftoverDamage = damageAmount - getHealth();
                this.turnlyHealth = 0;
                this.health = 0;
                try {
                    game.dealDamage(source.getId(), card.controller, new Damage(leftoverDamage));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

            } else { //Normal damage
                if (turnlyHealth >= damageAmount) {
                    turnlyHealth -= damageAmount;
                } else {
                    damageAmount -= turnlyHealth;
                    turnlyHealth = 0;
                    this.health -= Math.min(damageAmount, this.health);
                }
            }

            if (card != null && damage.mayKill && (getHealth() == 0 || source.hasCombatAbility(Deadly))) {
                game.destroy(source.getId(), card.getId());
            }

            if (source.hasCombatAbility(Lifelink)) {
                game.gainHealth(source.controller, dealtDamageAmount);
            }
            if (source.hasCombatAbility(Drain)) {
                game.heal(source.getId(), dealtDamageAmount);
            }
        };
    }

    public void loseMaxHealth(int i) {
        maxHealth = Math.max(maxHealth - i, 0);
    }


    public Combat(Game game, Card card, int health) {
        this(game, card, -1, health);
        canAttack = () -> false;
        canBlockGeneral = () -> false;
    }


    public final boolean canAttack() {
        return game.canAttack(card.controller) && canAttack.get();
    }

    public final boolean canBlock() {
        return game.canBlock(card.controller) && canBlockGeneral.get();
    }

    public final boolean canBlock(Card unit) {
        return canBlock.test(unit);
    }

    public final void unsetAttacking() {
        unsetAttacking.run();
    }

    public final void setBlocking(UUID unit) {
        setBlocking.accept(unit);
    }

    public final void unsetBlocking() {
        unsetBlocking.run();
    }

    public final void addBlocker(UUID blocker) {
        setBlocker.accept(blocker);
    }

    public final void newTurn() {
        newTurn.run();
    }

    public final void dealAttackDamage(boolean firstStrike) {
        dealAttackDamage.accept(firstStrike);
    }

    public final void dealBlockDamage() {
        dealBlockDamage.run();
    }

    public final void receiveDamage(Damage damage, Card source) {
        receiveDamage.accept(damage, source);
    }

    public final void clearTurnly() {
        turnlyAttack = 0;
        turnlyHealth = 0;
        turnlyShield = 0;
        turnlyCombatAbilityList.clear();
    }

    public final void clearCombatTargetData() {
        attackTarget = null;
        blockedAttacker = null;
        blockedBy = null;
    }

    public final void clear() {
        clearTurnly();
        clearCombatTargetData();
        deploying = false;
    }

    public final void endTurn() {
        clearTurnly();
        clearCombatTargetData();
    }

    public Types.Combat.Builder toCombatMessage() {
        return Types.Combat.newBuilder()
                .setAttack(getAttack())
                .setAttackTarget(attackTarget != null ? attackTarget.toString() : "")
                .setBlockedAttacker(blockedAttacker != null ? blockedAttacker.toString() : "")
                .setDeploying(!hasCombatAbility(CombatAbility.Blitz) && deploying)
                .setHealth(getHealth())
                .setShield(getShield())
                .addAllCombatAbilities(combatAbilityList.transformToStringList())
                .addAllCombatAbilities(turnlyCombatAbilityList.transformToStringList())
                .addAllPossibleBlockTargets(new Arraylist<String>((List<String>) game.getPossibleBlockTargets(canBlock).transform(c -> c.getId().toString())));
    }

    public void addCombatAbility(CombatAbility combatAbility, int i) {
        combatAbilityList.add(combatAbility, i);
    }

    public final void addCombatAbility(CombatAbility combatAbility) {
        addCombatAbility(combatAbility, 1);
    }

    public void addTurnlyCombatAbility(CombatAbility combatAbility, int i) {
        turnlyCombatAbilityList.add(combatAbility, i);
    }

    public final void addTurnlyCombatAbility(CombatAbility combatAbility) {
        addTurnlyCombatAbility(combatAbility, 1);
    }

    public void removeCombatAbility(CombatAbility combatAbility, int i) {
        combatAbilityList.decrease(combatAbility, i);
    }

    public void removeCombatAbility(CombatAbility combatAbility) {
        removeCombatAbility(combatAbility, 1);
    }

    public void addDamageEffect(BiConsumer<UUID, Damage> effect) {
        damageEffects.add(effect);
    }

    public final boolean hasCombatAbility(CombatAbility combatAbility) {
        return combatAbilityList.contains(combatAbility) || turnlyCombatAbilityList.contains(combatAbility);
    }

    public int getAttack() {
        return attack + turnlyAttack;
    }

    public int getShield() {
        return shield + turnlyShield;
    }

    public int getHealth() {
        return health + turnlyHealth;
    }


    public final void heal(int health) {
        if (this.health < maxHealth) {
            this.health = Math.min(maxHealth, this.health + health);
        }
    }

    //Do not use to lose health (e.g. negative input)
    //Also raises the max health
    public final void addAttackAndHealth(int attack, int health, boolean turnly) {
        if (turnly) {
            if (attack > 0)
                this.turnlyAttack += attack;

            if (health > 0) {
                this.turnlyHealth += health;
            }
        } else {
            if (attack > 0)
                this.attack += attack;

            if (health > 0) {
                this.health += health;
                this.maxHealth += health;
            }
        }
    }

    public boolean canDieFromBlock() {
        return getHealth() == 0 || (blockedBy != null && game.getCard(blockedBy).hasCombatAbility(Deadly));
    }

    public boolean canDieFromAttack() {
        return getHealth() == 0 || game.getCard(blockedAttacker).hasCombatAbility(Deadly);
    }

    public boolean isAttacking() {
        return attackTarget != null;
    }

    public final void setAttacking(UUID target) {
        setAttacking.accept(target);
    }

    public void setDeploying() {
        deploying = true;
    }

    public void loseHealth(int amount) {
        if (turnlyHealth >= amount) {
            turnlyHealth -= amount;
        } else {
            amount -= turnlyHealth;
            turnlyHealth = 0;
            this.health -= Math.min(amount, this.health);
            if (health <= 0) {
                game.destroy(card.getId());
            }
        }
    }

    public void setAttack(int i) {
        attack = i;
    }

    //Also sets the max health
    public void setHealth(int i) {
        health = i;
        maxHealth = i;
    }

    public boolean isDeploying() {
        return deploying;
    }

    public void addShield(int i, boolean turnly) {
        if (turnly) {
            turnlyShield += i;
        } else {
            shield += i;
        }
    }

    public void triggerDamageEffects(UUID targetId, Damage damage) {
        damageEffects.forEach(effect -> effect.accept(targetId, damage));
    }

    public void loseAttack(int i) {
        attack = Math.max(attack - i, 0);
    }

    public int drain(int amount) {
        int drained = 0;
        if (turnlyHealth <= amount){
            drained += turnlyHealth;
            turnlyHealth = 0;
            amount -= turnlyHealth;
            drained += Math.min(health, amount);
            health = Math.max(0, health - amount);
            if (health <= 0) {
                game.destroy(card.getId());
            }
        } else {
            drained += amount;
            turnlyHealth -= amount;
        }
        return drained;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void removeShield(int shieldAmount) {
        if (turnlyShield >= shieldAmount){
            turnlyShield -= shieldAmount;
        } else {
            shieldAmount -= turnlyShield;
            turnlyShield = 0;
            shield = Math.max(shield - shieldAmount, 0);
        }
    }

    public CounterMap<CombatAbility> getCombatAbilities() {
        CounterMap<CombatAbility> a = new CounterMap<>();
        a.merge(turnlyCombatAbilityList);
        a.merge(combatAbilityList);
        return a;
    }

    public enum CombatAbility {
        Deadly, //Done
        Defender, //Done
        FirstStrike, //Done
        Trample, //Done
        Evasive, //Done
        Vigilance, //Done
        Lifelink, //Done
        Reach, //Done
        Drain, //Done
        Unblockable, //Done
        Blitz,
        Decay,

        //Numbered abilities
        Regenerate //Done
    }

}
