/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.card.types.helpers.ActivatedAbility;
import com.visitor.card.types.specialtypes.ActivatableAsset;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.protocol.Types.Knowledge.GREEN;


/**
 * @author pseudo
 */
public class ExaminationTools extends ActivatableAsset {

    public ExaminationTools(Game game, String owner) {
        super(game, "Examination Tools", 2, new Hashmap(GREEN, 2),
                "1, Pay 3 life, Activate: \n" +
                        "    You can study one additional time.",
                owner);

        activatable.addActivatedAbility(new ActivatedAbility(
                () -> !depleted && game.hasEnergy(controller, 1),
                () -> {
                    game.spendEnergy(controller, 1);
                    game.payLife(controller, 3);
                    game.deplete(id);
                    game.addToStack(new AbilityCard(game, this,
                            "You can study one additional time.",
                            () -> {
                                game.addStudyCount(controller, 1);
                            }));
                }
        ));
    }
}
