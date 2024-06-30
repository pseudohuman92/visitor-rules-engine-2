/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Deathtouch;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class SwampWraith extends Unit {

    public SwampWraith(Game game, UUID owner) {
        super(game, "Swamp Wraith",
                1, new CounterMap(PURPLE, 1),
                "{P}{P} - {2}, {Use}, Discard a card: Put a 2/2 Zombie into play.\n" +
                        "Deplete 3 zombies you control: Draw a card and lose 1 life.",
                1, 1,
                owner, Deathtouch);
        subtypes.add(CardSubtype.Zombie, CardSubtype.Wizard);

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 2, "Put a 2/2 Zombie into play.",
                        () -> game.hasIn(controller, Game.Zone.Hand, Predicates::any, 1),
                        () -> game.discard(controller, 1),
                        () -> UnitToken.Zombie_2_2(game, controller).resolve())
                        .setDepleting()
                        .setKnowledgeRequirement(new CounterMap<>(PURPLE, 2)));

        activatable
                .addActivatedAbility(new ActivatedAbility(game, this, 0, "Draw a card and lose 1 life.",
                        () -> game.hasIn(controller, Game.Zone.Play, card -> !card.isDepleted() && card.hasSubtype(CardSubtype.Zombie), 3),
                        () -> game.selectFromZone(controller, Game.Zone.Play, card -> !card.isDepleted() && card.hasSubtype(CardSubtype.Zombie),
                                3, 3, "Select 3 zombies to deplete.").forEach(game::deplete),
                        () -> {
                            game.draw(controller, 1);
                            game.payHealth(controller, 1);
                        }));
    }
}
