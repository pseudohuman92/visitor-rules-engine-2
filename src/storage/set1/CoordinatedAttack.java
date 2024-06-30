/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Ritual;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;

import static com.visitor.game.Game.Zone.Play;
import static com.visitor.protocol.Types.Knowledge.GREEN;

/**
 * @author pseudo
 */
public class CoordinatedAttack extends Ritual {

    public CoordinatedAttack(Game game, String owner) {
        super(game, "Coordinated Attack", 5, new Hashmap(GREEN, 3),
                "Deal X damage to your opponent and each damageable they control.\n" +
                        "X = 1+ num of allies you control.", owner);

        playable.setResolveEffect(() -> {
            int x = game.getAllFrom(controller, Play, Predicates::isAlly).size() + 1;
            game.dealDamage(id, game.getOpponentId(controller), x);
            game.getAllFrom(game.getOpponentName(controller), Play, Predicates::isDamageable).forEach(
                    c -> {
                        game.dealDamage(id, c.id, x);
                    });
        });
    }
}

