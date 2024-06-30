/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Unit;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

/**
 * @author pseudo
 */
public class Dog extends Unit {

    public Dog(Game game, String owner) {
        super(game, "Dog",
                0, new Hashmap(),
                "",
                2, 4,
                owner);
    }
}
