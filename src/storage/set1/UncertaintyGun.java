/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Deck;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class UncertaintyGun extends Asset {

    int x;

    public UncertaintyGun(String owner) {
        super("Uncertainty Gun", 1, new Hashmap(BLUE, 2),
                "X, Sacrifice ~: \n" +
                        "  Look at opponent's deck. \n" +
                        "  Choose a card that costs X or less from it. \n" +
                        "  Transform chosen card into junk.", owner);
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return true;
    }

    @Override
    public void activate(Game game) {
        x = game.selectX(controller, game.getPlayer(controller).energy);
        game.sacrifice(id);
        game.addToStack(new AbilityCard(this,
                "  Look at opponent's deck. \n" +
                        "  Choose a card that costs " + x + " or less from it. \n" +
                        "  Transform chosen card into junk.",
                (y) -> {
                    Arraylist<UUID> s = game.selectFromList(controller,
                            game.getZone(game.getOpponentName(controller), Deck),
                            c -> {
                                return c.cost <= x;
                            }, 1, true);
                    if (!s.isEmpty()) {
                        game.transformToJunk(this, s.get(0));
                    }
                }));
    }

}
