package com.visitor.sets.base2;

import com.visitor.card.types.Ritual;
import com.visitor.game.Card;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class Contagion extends Ritual {
    public Contagion(Game game, UUID owner) {
        super(game, "Contagion", 4,
                new CounterMap<>(PURPLE, 2),
                "Destroy all units. Lose 1 health for each destroyed unit your opponent controls.",
                owner);
        playable.addResolveEffect(() -> game.getAllFrom(Base.Zone.Both_Play, Predicates::isUnit)
            .forEach(c -> {
                game.destroy(getId(), c.getId());
                if (!((Card) c).controller.equals(game.getOpponentId(controller))) {
                    game.loseHealth(controller, 1);
                }
            }));
    }
}
