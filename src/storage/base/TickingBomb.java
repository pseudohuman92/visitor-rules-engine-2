/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.base;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.EventChecker;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.protocol.Types.Counter;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;


/**
 * @author pseudo
 */
public class TickingBomb extends Asset {

    public TickingBomb(Game game, UUID owner) {
        super(game, "Ticking Bomb", 2, new CounterMap<>(PURPLE, 2),
                "Donate, Charge 2\n" +
                        "Trigger - At the beginning of your turn\n" +
                        "    Dischage 1. If {~} has no counters on it, sacrifice it and lose 5 health.\n" +
                        "1, {Use}: Charge 1\n" +
                        "3: Sacrifice {~}", owner);
        addCounters(Counter.CHARGE, 2);

        setDonating();

        activatable.addActivatedAbility(
                new ActivatedAbility(game, this, 1, "1, {Use}: Charge 1",
                        () -> addCounters(Counter.CHARGE, 1)).setDepleting());

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 3, "3: Sacrifice {~}", () -> game.sacrifice(id)));


        triggering.addEventChecker(new EventChecker(game, this,
                event -> {
                    removeCounters(Counter.CHARGE, 1);
                    if (!hasCounters(Counter.CHARGE, 1)) {
                        game.sacrifice(id);
                        game.payHealth(controller, 5);
                    }
                }).addStartOfControllerTurnChecker().createAbility("Dischage 1. If {~} has no counters on it, sacrifice it and lose 5 health."));
    }
}