/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.properties.Triggering;
import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.Event;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.BLUE;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class GU01 extends Unit {

    public GU01(Game game, UUID owner) {
        super(game, "GU01",
                5, new CounterMap(BLUE, 1).add(GREEN, 1),
                "Whenever you play a green unit, search your library for a unit, shuffle your library and put that card on top of it.\n" +
                        "Whenever you play a blue unit, Draw the top card of your deck if it's a unit.",
                2, 2,
                owner);

        triggering = new Triggering(game, this)
                .addEventChecker(new EventChecker(game, this,
                        event -> {
                            Arraylist<UUID> selectedUnit = game.selectFromZone(controller,
                                    Game.Zone.Deck,
                                    Predicates::isUnit,
                                    game.hasIn(controller, Game.Zone.Deck, Predicates::isUnit, 1)?1:0,
                                    game.hasIn(controller, Game.Zone.Deck, Predicates::isUnit, 1)?1:0,
                                    "Choose a unit from your deck.");
                            if (!selectedUnit.isEmpty()) {
                                game.shuffleDeck(controller);
                                game.putToTopOfDeck(selectedUnit.get(0));
                            }
                        })
                        .addTypeChecker(Event.EventType.Play_Card)
                        .addCardChecker(playedCard -> playedCard.controller.equals(controller))
                        .addCardChecker(Predicates::isUnit)
                        .addCardChecker(Predicates.isColor(GREEN))
                        .createAbility("Whenever you play a green unit, search your library for a unit, shuffle your library and put that card on top of it."));

        triggering.addEventChecker(new EventChecker(game, this,
                event -> {
                    if (game.getTopCardsFromDeck(controller, 1).get(0).hasType(CardType.Unit)) {
                        game.draw(controller, 1);
                    }
                })
                .addTypeChecker(Event.EventType.Play_Card)
                .addCardChecker(Predicates.controlledBy(controller))
                .addCardChecker(Predicates::isUnit)
                .addCardChecker(Predicates.isColor(BLUE))
                .createAbility("Whenever you play a blue unit, Draw the top card of your deck if it's a unit."));
    }
}
