/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.card.types;

import com.visitor.game.Card;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

/**
 * @author pseudo
 */
public class Junk extends Card {

    public Junk(Game game, UUID owner) {
        super(game, "Junk", new CounterMap<>(), CardType.Junk, "Junk can't be played or studied.", owner);
    }

}
