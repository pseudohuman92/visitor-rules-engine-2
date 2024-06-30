/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Ritual;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.protocol.Types;

import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class MassEnergyConversion extends Ritual {

    int x = -1;

    public MassEnergyConversion(String owner) {
        super("Mass Energy Conversion", 0, new Hashmap(GREEN, 3),
                "You may study X additional times", owner);
    }

    @Override
    protected void beforePlay(Game game) {
        x = game.selectX(controller, game.getEnergy(controller) / 2);
        cost += 2 * x;
        text = "You may study " + x + " additional times";
    }

    @Override
    public void duringResolve(Game game) {
        game.addStudyCount(controller, x);
        text = "You may study X additional times";
        cost = 0;
        x = -1;
    }

    @Override
    public Types.Card.Builder toCardMessage() {
        return super.toCardMessage()
                .setCost(x == -1 ? "2X" : ("" + cost));
    }
}

