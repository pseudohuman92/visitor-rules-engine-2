/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.HelperFunctions;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Blitz;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class BlitzRobbers extends Unit {

    public BlitzRobbers(Game game, UUID owner) {
        super(game, "Blitz Robbers",
                1, new CounterMap(RED, 2),
                "Fast\nWhen {~} deals attack damage to a player, draw a card.",
                1, 1,
                owner, Blitz);
        playable.setFast();
        combat.addDamageEffect((targetId, damage) -> HelperFunctions.runIf(game.isPlayer(targetId)&&damage.amount > 0, () -> game.draw(controller, 1)));
    }
}
