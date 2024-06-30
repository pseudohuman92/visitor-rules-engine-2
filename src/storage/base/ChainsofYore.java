/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Lifelink;
import static com.visitor.helpers.Predicates.*;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class ChainsofYore extends Unit {

    public ChainsofYore(Game game, UUID owner) {
        super(game, "Chains of Yore",
                1, new CounterMap(YELLOW, 1),
                "{2}, {Use}: Deplete another target unit.",
                1, 1,
                owner, Lifelink);

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 2, "{2}, {Use}: Deplete another target unit.")
                .setTargeting(Game.Zone.Both_Play, and(not(Predicates::isDepleted), anotherUnit(id)), 1, 1, game::deplete)
                .setDepleting());
    }
}
