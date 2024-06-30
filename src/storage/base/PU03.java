/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.Ability;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class PU03 extends Unit {

    public PU03(Game game, UUID owner) {
        super(game, "PU03",
                4, new CounterMap(BLUE, 1).add(PURPLE, 1),
                "When {~} enters play, return a blue or purple unit you control to its controller’s hand.\n" +
                        "Whenever {~} deals combat damage to a player, look at that player’s hand and \n" +
                        "choose a card from it. The player discards that card.",
                2, 2,
                owner, Combat.CombatAbility.Flying);

        addEnterPlayEffectOnStack(null, "When {~} enters play, return a blue or purple unit you control to its controller’s hand.",
                () -> {
                    UUID returnedUnit = game.selectFromZone(controller, Game.Zone.Play, Predicates.and(Predicates::isUnit, Predicates.or(Predicates.isColor(BLUE), Predicates.isColor(PURPLE))), 1, 1, "Select a blue or purple unit.").get(0);
                    game.returnToHand(returnedUnit);
                });

        combat.addDamageEffect(
                (targetId, damage) -> {
                    if (game.isPlayer(targetId)) {
                        game.addToStack(new Ability(game, this, "Whenever {~} deals combat damage to a player, you may play target \n" +
                                "spell from that player’s discard pile without paying its cost. \n" +
                                "Purge the spell after it resolves.",
                                () -> {
                                    Arraylist<UUID> maybePlay = game.selectFromZone(game.getOpponentId(controller), Game.Zone.Hand, card -> card.canPlay(false), game.hasIn(game.getOpponentId(controller), Game.Zone.Hand, Predicates::any, 1)?1:0, game.hasIn(game.getOpponentId(controller), Game.Zone.Hand, Predicates::any, 1)?1:0, "Select a card to to discard.");
                                    if (!maybePlay.isEmpty()) {
                                        game.discard(game.getOpponentId(controller), maybePlay.get(0));
                                    }
                                }));

                    }

                }
        );
    }
}
