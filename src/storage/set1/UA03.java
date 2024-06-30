/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.Spell;
import com.visitor.game.Game;
import com.visitor.game.Player;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

import static com.visitor.helpers.UUIDHelper.getInList;
import static com.visitor.helpers.UUIDHelper.getNotInList;
import static com.visitor.protocol.Types.Knowledge.BLUE;

/**
 * @author pseudo
 */
public class UA03 extends Spell {

    /**
     * @param owner
     */
    public UA03(String owner) {
        super("UA03", 1, new Hashmap(BLUE, 1),
                "Look at the top 4 cards of your deck. Put a kit among them into play. Put rest to the bottom.", owner);
    }

    @Override
    protected void duringResolve(Game game) {
        Player p = game.getPlayer(controller);
        Arraylist<Card> topCards = p.deck.extractFromTop(4);
        Arraylist<UUID> s = game.selectFromList(controller, topCards, c -> {
            return c.subtypes.contains("Kit");
        }, 1, true);
        Arraylist<Card> selected = getInList(topCards, s);
        Arraylist<Card> notSelected = getNotInList(topCards, s);
        p.deck.putToBottom(notSelected);
        selected.forEach(c -> {
            c.resolve(game);
        });
    }

}
