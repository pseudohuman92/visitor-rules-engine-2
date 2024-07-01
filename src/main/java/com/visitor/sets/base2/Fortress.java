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
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Defender;
import static com.visitor.card.properties.Combat.CombatAbility.Evasive;
import static com.visitor.game.parts.Base.Zone.Both_Play;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class Fortress extends Unit {

    public Fortress(Game game, UUID owner) {
        super(game, "Fortress",
                5, new CounterMap(YELLOW, 3),
                "{Use}: Target unit gains Shield 2",
                0, 5,
                owner, Defender);
        addShield(5, false);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{Use}: Target unit gains Shield 2").setDepleting()
                .addTargeting(Both_Play, Predicates::isUnit, 1, 1, "Choose a unit to add Shield 2", t -> {
                    game.addShield(t, 2, false);
                }, false));
    }
}
