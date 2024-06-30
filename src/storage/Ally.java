/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.card.types;

import com.visitor.card.properties.*;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Card;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.protocol.Types;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.Math.max;

/**
 * @author pseudo
 */
public abstract class Ally extends Card {


    public int delayCounter;
    public AbilityCard delayedAbility;
    public int loyalty;

    public Ally(Game game, String name, int cost,
                CounterMap<Types.Knowledge> knowledge,
                String text, int health, UUID owner) {

        super(game, name, knowledge, CardType.Ally, text, owner);

        delayCounter = 0;
        loyalty = 0;
        delayedAbility = null;

        playable = new Playable(game, this, cost).setSlow().setPersistent();
        studiable = new Studiable(game, this);
        combat = new Combat(game, this, health);
        triggering = new Triggering(game, this);
        activatable = new Activatable(game, this);

        /*
        triggering.addEventChecker(new EventChecker(game, this,
                event -> {
                    if (delayCounter > 0) {
                        decreaseDelayCounter(game, 1);
                    }
                }).addStartOfControllerTurnCondition());
        */

    }

    public void decreaseDelayCounter(Game game, int count) {
        delayCounter = max(0, delayCounter - count);
        if (delayCounter == 0 && delayedAbility != null) {
            newTurn();
            game.addToStack(delayedAbility);
            delayedAbility = null;
        }
    }

    @Override
    public void newTurn() {
        if (delayCounter == 0) {
            super.newTurn();
        }
    }

    @Override
    public void leavePlay() {
        delayCounter = 0;
        delayedAbility = null;
        super.leavePlay();
    }

    protected void addPlusLoyaltyAbility(int cost, String text, int plusLoyalty, Runnable additionalEffects, Supplier<Boolean> canActivateAdditional, Runnable additionalCost) {
        Supplier<Boolean> finalCanActivateAdditional = canActivateAdditional != null ? canActivateAdditional : () -> true;
        Runnable finalAdditionalCost = additionalCost != null ? additionalCost : () -> {
        };
        Runnable finalAdditionalEffects = additionalEffects != null ? additionalEffects : () -> {
        };

        activatable.addActivatedAbility(new ActivatedAbility(game, this, cost, text,
                () -> {
                    loyalty += plusLoyalty;
                    finalAdditionalEffects.run();
                })
                .addCanActivateAdditional(finalCanActivateAdditional)
                .addBeforeActivate(finalAdditionalCost)
                .setDepleting()
                .setSlow());
    }

    protected void addMinusLoyaltyAbility(int cost, String text, int minusLoyalty, int delay, ActivatedAbility ability, Supplier<Boolean> canActivateAdditional, Runnable additionalCost) {
        Supplier<Boolean> finalCanActivateAdditional = canActivateAdditional != null ? canActivateAdditional : () -> true;
        Runnable finalAdditionalCost = additionalCost != null ? additionalCost : () -> {
        };

        activatable.addActivatedAbility(new ActivatedAbility(game, this, cost, text,
                () -> {
                    delayCounter = delay;
                    delayedAbility = new AbilityCard(game, this, ability);
                })
                .addBeforeActivate(() -> {
                    loyalty -= minusLoyalty;
                    finalAdditionalCost.run();
                })
                .addCanActivateAdditional(() -> loyalty >= minusLoyalty && finalCanActivateAdditional.get())
                .setSlow()
                .setDepleting());
    }

    protected void addMinusLoyaltyAbilityWithX(int cost, String text, int minusLoyalty, int delay, Function<Integer, ActivatedAbility> ability,
                                               Function<Integer, Boolean> canActivateAdditional, Consumer<Integer> additionalCost, int maxX) {
        Function<Integer, Boolean> finalCanActivateAdditional = canActivateAdditional != null ? canActivateAdditional : x -> true;
        Consumer<Integer> finalAdditionalCost = additionalCost != null ? additionalCost : x -> {
        };
        final int[] x = {0};

        activatable.addActivatedAbility(new ActivatedAbility(game, this, cost, text,
                () -> {
                    delayCounter = delay;
                    delayedAbility = new AbilityCard(game, this, ability.apply(x[0]));
                })
                .addBeforeActivate(() -> {
                    x[0] = game.selectX(controller, maxX);
                    loyalty -= minusLoyalty;
                    finalAdditionalCost.accept(x[0]);
                })
                .addCanActivateAdditional(() -> loyalty >= minusLoyalty && finalCanActivateAdditional.apply(x[0]))
                .setSlow()
                .setDepleting());
    }

    @Override
    public Types.CardP.Builder toCardMessage() {
        return super.toCardMessage()
                .setDelay(delayCounter)
                .setLoyalty(loyalty);
    }

/*
	@Override
	public void copyPropertiesFrom (Card c) {
		super.copyPropertiesFrom(c);
		if (c instanceof Ally) {
			delayCounter = ((Ally) c).delayCounter;
			loyalty = ((Ally) c).loyalty;

		}
	}
*/
}
