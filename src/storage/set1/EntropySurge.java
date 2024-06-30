/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.card.types.helpers.ActivatedAbility;
import com.visitor.card.types.specialtypes.ActivatableAsset;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class EntropySurge extends ActivatableAsset {

    UUID target;

    public EntropySurge(Game game, String owner) {
        super(game, "Entropy Surge", 4, new Hashmap(BLACK, 2),
                "Sacrifice an Asset: Gain 1 Energy. If that asset is owned by the opponent gain 1 additional energy.", owner);

        activatable.addActivatedAbility(new ActivatedAbility(
                () -> game.hasIn(controller, Play, Predicates::isAsset, 1),
                () -> {
                    target = game.selectFromZone(controller, Play, Predicates::isAsset, 1, false).get(0);
                    game.sacrifice(target);
                    game.addToStack(new AbilityCard(game, this,
                            controller + " gains " + (game.ownedByOpponent(target) ? 2 : 1) + " energy",
                            () -> {
                                game.addEnergy(controller, (game.ownedByOpponent(target) ? 2 : 1));
                            }, target));
                }
        ));
    }
}
