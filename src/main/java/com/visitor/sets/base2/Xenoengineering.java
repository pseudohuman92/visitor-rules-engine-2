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

/**
 * @author pseudo
 */
public class Xenoengineering extends Tome {

    public Xenoengineering(Game game, UUID owner) {
        super(game, "Xenoengineering", "Study: Gain {B}{B}", owner, new CounterMap<>(BLUE, 2));
    }

}
