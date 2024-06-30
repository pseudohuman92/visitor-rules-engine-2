/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Vigilance;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class UnendingFields extends Unit {

    public UnendingFields(Game game, UUID owner) {
        super(game, "Unending Fields",
                5, new CounterMap(YELLOW, 2),
                "When {~} enters play, destroy up to one target unit. Its controller plays a 3/3 Golem.",
                4, 6,
                owner, Vigilance);
        playable.setTargetSingleUnit(null, null, targetId -> {
            UUID targetedCardController = game.getCard(targetId).controller;
            game.destroy(id, targetId);
            UnitToken.Golem_3_3(game, targetedCardController).resolve();
        }, null);
    }
}
