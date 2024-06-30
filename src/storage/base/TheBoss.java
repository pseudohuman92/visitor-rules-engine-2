/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;


import com.visitor.card.types.Ally;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class TheBoss extends Ally {

    public TheBoss(Game game, UUID owner) {
        super(game, "The Boss", 2, new CounterMap<>(PURPLE, 1),
                "{Slow} | Discard a card, {Use}:    \n" +
                        "    +1 {Loyalty}. Opponent discards a card.\n" +
                        "\n" +
                        "{Condition} - Opponent has no cards in hand\n" +
                        "  {Slow} | -2 {Loyalty}, {Use}: \n" +
                        "       {Delay} 1 - Deal 2 damage",
                1,
                owner);

        addPlusLoyaltyAbility(0, "Discard a card, {Use}:\n\t+1 {Loyalty}. Opponent discards a card.", 1,
                () -> game.discard(game.getOpponentId(controller), 1),
                () -> game.hasIn(controller, Hand, Predicates::any, 1),
                () -> game.discard(controller, 1));

        addMinusLoyaltyAbility(0, "-2 {Loyalty}, {Use}:\n\t{Delay} 1 - Deal 2 damage", 2, 1,
                new ActivatedAbility(game, this, 0, "Deal 2 Damage")
                        .setTargetingForDamage(targetID -> game.dealDamage(id, targetID, 2)),
                () -> !game.hasIn(game.getOpponentId(controller), Hand, Predicates::any, 1),
                null);

    }

}

