/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Vigilance;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class AirBlaster extends Unit {

    public AirBlaster(Game game, UUID owner) {
        super(game, "Air Blaster",
                3, new CounterMap(YELLOW, 1),
                "When {~} enters play, return up to one target unit to its controller's hand.",
                2, 2,
                owner, Vigilance);
        addShield(2, false);
        addEnterPlayEffectOnStack(null, "Return up to one target unit to its controller's hand.",
                () ->{
            game.selectFromZone(controller, Base.Zone.Both_Play, Predicates::isUnit, 0, 1, "Return up to one target unit to its controller's hand.")
                    .forEach(game::returnToHand);
                });
    }
}
