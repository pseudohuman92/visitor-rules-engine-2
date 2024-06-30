/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Game.Zone.Hand;
import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.BLUE;


/**
 * @author pseudo
 */
public class SelfdestructNode extends Asset {

    public SelfdestructNode(String owner) {
        super("Self-destruct Node", 2, new Hashmap(BLUE, 2),
                "Discard 2: Transform ~ into AI01.", owner);
        subtypes.add("Kit");
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.getZone(controller, Hand).size() >= 2;
    }

    @Override
    public void activate(Game game) {
        game.discard(controller, 2);
        game.addToStack(new AbilityCard(this, "Transform ~ into AI01.",
                (x) -> {
                    if (game.isIn(controller, id, Play)) {
                        game.transformTo(this, this, new Meltdown(this));
                    }
                }));
    }
}
