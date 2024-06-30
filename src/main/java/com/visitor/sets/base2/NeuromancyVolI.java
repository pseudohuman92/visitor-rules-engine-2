package com.visitor.sets.base2;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.visitor.card.types.Tome;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class NeuromancyVolI extends Tome {

    public NeuromancyVolI(Game game, UUID owner) {
        super(game, "Neuromancy Vol. I", "Study: Gain {B}{P}", owner, new CounterMap<>(PURPLE, 1).add(BLUE, 1));
    }

}
