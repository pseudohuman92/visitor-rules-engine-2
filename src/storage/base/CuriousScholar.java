/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;


import com.visitor.card.types.Ally;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.game.Event.EventType.Study;
import static com.visitor.game.parts.Base.Zone.Deck;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class CuriousScholar extends Ally {

    public CuriousScholar(Game game, UUID owner) {
        super(game, "Curious Scholar", 2, new CounterMap<>(GREEN, 1),
                "Trigger - When you study\n" +
                        "    +1 Loyalty\n" +
                        "-2 Loyalty, {Use}: \n" +
                        "    Delay 1 - Search your deck for a Green card and draw it",
                3,
                owner);

        triggering.addEventChecker(new EventChecker(game, this, event -> loyalty++).addTypeChecker(Study).addPlayerChecker(c -> c.equals(controller))
                .createAbility("+1 Loyalty"));

        addMinusLoyaltyAbility(0, "-2 Loyalty, {Use}: Delay 1 - Search your deck for a green card and draw it", 2, 1,
                new ActivatedAbility(game, this, 0, "Search your deck for a green card and draw it",
                        () -> {
                            Arraylist<UUID> maybeSelected = game.selectFromZone(controller, Deck, Predicates::isGreen, game.hasIn(controller,
                                    Deck, Predicates::isGreen,
                                    1)?1:0, game.hasIn(controller,
                                    Deck, Predicates::isGreen,
                                    1)?1:0, "Select a green card");
                            if (!maybeSelected.isEmpty()) {
                                game.draw(controller, maybeSelected.get(0));
                            }
                        }), null, null);
    }

}

