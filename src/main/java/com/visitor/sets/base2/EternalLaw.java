package com.visitor.sets.base2;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.visitor.card.types.Tome;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class EternalLaw extends Tome {

    public EternalLaw(Game game, UUID owner) {
        super(game, "Eternal Law", "Study: Gain {P}{Y}", owner, new CounterMap<>(PURPLE, 1).add(YELLOW, 1));
    }

}

