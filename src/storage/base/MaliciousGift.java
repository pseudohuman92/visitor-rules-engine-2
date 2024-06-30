/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class MaliciousGift extends Asset {

    public MaliciousGift(Game game, UUID owner) {
        super(game, "Malicious Gift", 2, new CounterMap<>(PURPLE, 1),
                "Donate\n" +
                        "Trigger - End of your turn\n" +
                        "    {~} deals 3 damage to you or target damageable you control, then sacrifice {~}.", owner);

        setDonating();

        triggering.addEventChecker(new EventChecker(game, this,
                event -> {
                    UUID target = game.selectDamageTargetsConditional(controller, Predicates.and(Predicates.controlledBy(controller), Predicates::isDamageable), p -> p.id.equals(controller), 1, 1, "").get(0);
                    game.dealDamage(id, target, 3);
                    game.sacrifice(id);
                }).addEndOfControllerTurnChecker()
                .createAbility("{~} deals 3 damage to you or target damageable you control, then sacrifice {~}."));
    }
}
