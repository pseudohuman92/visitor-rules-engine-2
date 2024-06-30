package com.visitor.sets.base2;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.visitor.card.types.Tome;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class PersonalRelations extends Tome {

    public PersonalRelations(Game game, UUID owner) {
        super(game, "Personal Relations", "Study: Gain {G}{G}", owner, new CounterMap<>(GREEN, 2));
    }

}
