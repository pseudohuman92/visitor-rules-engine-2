/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.types.Cantrip;
import com.visitor.game.Card;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class OutofCommision extends Cantrip {

    public OutofCommision(Game game, UUID owner) {
        super(game, "Out of Commision",
                2, new CounterMap(PURPLE, 2),
                "Destroy target unit with cost 4 or less.",
                owner);
        playable.addTargetSingleUnit(Base.Zone.Both_Play, c->{ return ((Card) c).getCost() <= 4; }, i -> {game.destroy(getId(), i);}, "Choose a unit to sacrifice.", false);
    }
}
