/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.Card;
import com.visitor.game.Event;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.helpers.HelperFunctions.runIf;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class SkyMarcher extends Unit {

    public SkyMarcher(Game game, UUID owner) {
        super(game, "Sky Marcher",
                4, new CounterMap(RED, 3),
                "Whenever {~} deals attack damage to a player, you gain that much energy this turn.",
                3, 5,
                owner, Combat.CombatAbility.Evasive);
        combat.addDamageEffect((tid, d) ->  runIf(game.isPlayer(tid), () -> game.addEnergy(controller, d.amount, true)));
    }
}
