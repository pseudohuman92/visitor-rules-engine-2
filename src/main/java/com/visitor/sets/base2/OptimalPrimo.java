/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.*;
import static com.visitor.game.parts.Base.Zone.Both_Play;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class OptimalPrimo extends Unit {

    public OptimalPrimo(Game game, UUID owner) {
        super(game, "Optimal Primo",
                6, new CounterMap(YELLOW, 4),
                "",
                8, 8,
                owner, Trample, Vigilance);
        addShield(8, false);
    }
}
