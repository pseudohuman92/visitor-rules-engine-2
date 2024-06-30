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
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class ArtofWar extends Tome {

    public ArtofWar(Game game, UUID owner) {
        super(game, "Art of War", "Study: Gain {B}{R}", owner, new CounterMap<>(BLUE, 1).add(RED, 1));
    }
}
