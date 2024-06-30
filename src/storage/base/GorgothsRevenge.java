/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Trample;
import static com.visitor.protocol.Types.Knowledge.RED;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class GorgothsRevenge extends Unit {

    public GorgothsRevenge(Game game, UUID owner) {
        super(game, "Gorgoth's Revenge",
                2, new CounterMap(YELLOW, 1),
                "{R} - {4}: {~} gains +2/+0 and Trample.",
                3, 1,
                owner);

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 4, "{R} - {4}: {~} gains +2/+0 and trample until end of turn.",
                () -> game.runIfInPlay(id,
                        () -> {
                            game.addAttackAndHealth(id, 2, 0);
                            game.addCombatAbility(id, Trample);
                        }))
                .setKnowledgeRequirement(new CounterMap<>(RED, 1)));
    }
}
