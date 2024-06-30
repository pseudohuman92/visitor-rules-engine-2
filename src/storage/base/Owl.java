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

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.card.properties.Combat.CombatAbility.Lifelink;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class Owl extends Unit {

    public Owl(Game game, UUID owner) {
        super(game, "Owl",
                3, new CounterMap(BLUE, 2),
                "{1}, {Use}: Draw a card.",
                2, 2,
                owner, Flying, Lifelink);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 1, "Draw a card",
                        () -> game.draw(controller, 1))
                        .setDepleting());
    }
}
