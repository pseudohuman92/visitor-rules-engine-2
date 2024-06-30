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

import static com.visitor.game.parts.Base.Zone.Play;
import static com.visitor.helpers.Predicates.and;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class GrandOrator extends Ally {

    public GrandOrator(Game game, UUID owner) {
        super(game, "Grand Orator", 2, new CounterMap<>(GREEN, 2),
                "2, {Use}: +1 Loyalty.\n" +
                        "-1 Loyalty, {Use}:\n" +
                        "      Delay 1 - All other allies you control gain 1 Loyalty", 2,
                owner);

        addPlusLoyaltyAbility(2, "2, {Use}: +1 Loyalty.", 1, null, null, null);

        addMinusLoyaltyAbility(0, "1 Loyalty, {Use}: Delay 1 - All other allies you control gain 1 Loyalty", 1, 1,
                new ActivatedAbility(game, this, 0, "All other allies you control gain 1 Loyalty",
                        () -> game.forEachInZone(controller, Play, and(Predicates::isAlly, Predicates.anotherCard(id)),
                                cardId -> ((Ally) game.getCard(cardId)).loyalty++)), null, null);
    }
}

