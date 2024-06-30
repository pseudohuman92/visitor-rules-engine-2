/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.*;

/**
 * @author pseudo
 */
public class Baiter extends Unit {

    public Baiter(Game game, UUID owner) {
        super(game, "Baiter",
                2, new CounterMap(PURPLE, 1),
                "{1}, Purge a unit from your discard pile: Create an insect.\n" +
                        "{2}, Sacrifice a unit: Draw a card.",
                1, 1,
                owner);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 1, "{1}, Purge a unit from your discard pile: Create an insect.", () -> UnitToken.Insect_1_1(game, controller).resolve())
                .addTargeting(Base.Zone.Discard_Pile, Predicates::isUnit, 1, 1, "Select a unit to purge.", game::purge, true));
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 1, "{2}, Sacrifice a unit: Draw a card.", () -> game.draw(controller, 1))
                .addTargeting(Base.Zone.Play, Predicates::isUnit, 1, 1, "Select a unit to sacrifice.", game::sacrifice, true));
    }
}
