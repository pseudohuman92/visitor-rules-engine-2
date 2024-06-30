package com.visitor.sets.base2;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.visitor.card.types.Tome;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class PathofEnlightenment extends Tome {

    public PathofEnlightenment(Game game, UUID owner) {
        super(game, "Path of Enlightenment", "Study: Gain {Y}{Y}", owner, new CounterMap<>(YELLOW, 2));
    }

}
