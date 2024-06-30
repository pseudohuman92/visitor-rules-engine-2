/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.card.types.helpers.Ability;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.Card;
import com.visitor.game.Event;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.GREEN;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class GP01 extends Unit {

    public GP01(Game game, UUID owner) {
        super(game, "GP01",
                5, new CounterMap(GREEN, 1).add(PURPLE, 1),
                "At the start of your turn, your opponent discard a card.\n" +
                        "Whenever an opponent discards a card, you create a 1/1 green Elf.",
                4, 4,
                owner);

        triggering.addEventChecker(new EventChecker(game, this,
                event -> game.discard(game.getOpponentId(controller), 1))
                .addStartOfControllerTurnChecker()
                .createAbility("At the start of your turn, your opponent discard a card."));

        triggering.addEventChecker(new EventChecker(game, this,
                event -> {
                    ((Arraylist<Card>) event.data.get(1)).forEach(card ->
                            game.addToStack(new Ability(game, this, "Whenever an opponent discards a card, you create a 1/1 green Elf.",
                                    () -> UnitToken.Elf_1_1(game, controller).resolve())));

                })
                .addTypeChecker(Event.EventType.Discard)
                .addPlayerChecker(playerName -> playerName.equals(game.getOpponentId(controller)))
        );
    }
}
