/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base;

import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.sets.token.UnitToken;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Flying;
import static com.visitor.card.properties.Combat.CombatAbility.Lifelink;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

/**
 * @author pseudo
 */
public class IsthmusofSerenity extends Unit {

    public IsthmusofSerenity(Game game, UUID owner) {
        super(game, "Isthmus of Serenity",
                6, new CounterMap(PURPLE, 3),
                "When {~} enters play, put X 1/1 Bat with flying into play where X equal to your {P}",
                4, 4,
                owner, Flying, Lifelink);

        addEnterPlayEffectOnStack(null, "When {~} enters play, put X 1/1 Bat with flying into play where X equal to your {P}",
                () -> {
                    int x = game.getKnowledgeCount(controller, PURPLE);
                    for (int i = 0; i < x; i++) {
                        UnitToken.Bat_1_1(game, controller).resolve();
                    }
                });
    }
}
