/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;

import static com.visitor.game.Event.playersTurnStart;
import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.BLACK;

/**
 * @author pseudo
 */
public class CreepingPoison extends TriggeringPassive {

    public CreepingPoison(Game game, String owner) {
        super(game, "Creeping Poison", 3, new Hashmap(BLACK, 2),
                "Donate\n" +
                        "Trigger - At the start of your turn\n" +
                        "    Deal 1 damage to your controller.", owner);

        triggering.addEventChecker(
                (event) -> {
                    if (playersTurnStart(event, controller)) {
                        game.addToStack(
                                new AbilityCard(game, this,
                                        "Deal 1 damage to your controller.",
                                        () -> {
                                            game.dealDamage(id, game.getUserId(controller), 1);
                                        },
                                        game.getUserId(controller)));
                    }
                });
        playable.addAfterResolve(() -> {
            game.addToStack(new AbilityCard(game, this,
                    "Donate " + name,
                    () -> {
                        game.donate(id, game.getOpponentName(controller), Play);
                    }
            ));
        });
    }
}
