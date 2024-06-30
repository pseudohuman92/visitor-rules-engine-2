package com.visitor.sets.base2;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.visitor.card.types.Tome;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class JustWar extends Tome {

    public JustWar(Game game, UUID owner) {
        super(game, "Just War", "Study: Gain {R}{Y}", owner, new CounterMap<>(RED, 1).add(YELLOW, 1));
    }

}

