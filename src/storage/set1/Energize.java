/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Spell;
import com.visitor.game.Deck;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import static com.visitor.game.Game.Zone.Deck;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class Energize extends Spell {

    public Energize(Game game, String owner) {
        super(game, "Energize", 3, new Hashmap(GREEN, 1),
                "Study the top studiable card of your deck", owner);

        playable.setResolveEffect(() -> {
            game.studyCardIrregular(controller, ((Deck) game.getZone(controller, Deck)).getTopmost(Predicates::isStudyable).id);
        });
    }
}

