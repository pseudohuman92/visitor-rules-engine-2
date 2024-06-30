/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Asset;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Blitz;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class Barracks extends Asset {

    public Barracks(Game game, UUID owner) {
        super(game, "Barracks",
                3, new CounterMap(RED, 1),
                "{Use}: Create a Stormtrooper",
                owner);
        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{Use}: Create a Stormtrooper").setDepleting());
    }
}
