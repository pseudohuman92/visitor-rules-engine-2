/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;


import com.visitor.card.types.Ally;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

/**
 * @author pseudo
 */
public class GoblinAlly extends Ally {

    public GoblinAlly(String owner) {
        super("Goblin Ally", 0, new Hashmap(),
                "", 2,
                owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return false;
    }


    @Override
    public void activate(Game game) {
    }

}

