/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Card;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.card.types.helpers.ActivatedAbility;
import com.visitor.card.types.specialtypes.ActivatableAsset;
import com.visitor.game.Game;
import com.visitor.game.Player;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.helpers.UUIDHelper.getInList;
import static com.visitor.helpers.UUIDHelper.getNotInList;
import static com.visitor.protocol.Types.Knowledge.BLUE;


/**
 * @author pseudo
 */
public class DataRecompiler extends ActivatableAsset {

    public DataRecompiler(Game game, String owner) {
        super(game, "Data Recompiler", 1, new Hashmap(BLUE, 1),
                "1, Activate: Look at the top 2 cards of your library. "
                        + "You may discard any number of them. Put the rest in the same order.", owner);

        activatable.addActivatedAbility(new ActivatedAbility(
                () -> !depleted && game.hasEnergy(controller, 1),
                () -> {
                    game.deplete(id);
                    game.spendEnergy(controller, 1);
                    game.addToStack(new AbilityCard(game, this, "Look at the top 2 cards of your library. "
                            + "You may discard any number of them. Put the rest in the same order.",
                            () -> {
                                Player player = game.getPlayer(controller);
                                Arraylist<Card> candidates = player.deck.extractFromTop(2);
                                Arraylist<UUID> selected = game.selectFromList(controller, candidates, Predicates::any, 2, true);
                                player.deck.putToTop(getNotInList(candidates, selected));
                                player.scrapyard.addAll(getInList(candidates, selected));
                            }));
                }
        ));
    }
}
