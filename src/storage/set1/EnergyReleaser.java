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
import static com.visitor.protocol.Types.Knowledge.RED;


/**
 * @author pseudo
 */
public class EnergyReleaser extends ActivatableAsset {

    public EnergyReleaser(Game game, String owner) {
        super(game, "Energy Releaser", 1, new Hashmap(RED, 2),
                "Sacrifice an Asset, Activate: \n" +
                        "  Deal X damage. \n" +
                        "  X = cost of sacrificed asset.", owner);

        activatable.addActivatedAbility(new ActivatedAbility(
                () -> !depleted && game.hasIn(controller, Play, Predicates::isAsset, 1),
                () -> {
                    UUID selection = game.selectFromZone(controller, Play, Predicates::isAsset, 1, false).get(0);
                    int x = game.getCard(selection).playable.getCost();
                    game.sacrifice(selection);
                    game.deplete(id);
                    UUID target = game.selectDamageTargets(controller, 1, false).get(0);
                    game.addToStack(new AbilityCard(game, this,
                            "Deal " + x + " damage",
                            () -> {
                                game.dealDamage(id, target, x);
                            }, selection)
                    );
                }
        ));
    }
}
