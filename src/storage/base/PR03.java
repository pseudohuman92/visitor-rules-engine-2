package com.visitor.sets.base;

import com.visitor.card.types.Ritual;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.game.parts.Base.Zone.Hand;
import static com.visitor.protocol.Types.Knowledge.PURPLE;

public class PR03 extends Ritual {

    public PR03(Game game, UUID owner) {
        super(game, "PR03", 1,
                new CounterMap<>(PURPLE, 1),
                "Target opponent reveals their hand. You choose a unit card from it. \n" +
                        "That player discards that card.",
                owner);

        playable
                .addResolveEffect(() -> {
                    boolean hasUnit = game.hasIn(game.getOpponentId(playable.card.controller), Hand, Predicates::isUnit, 1);
                    Arraylist<UUID> discardedCard = game.selectFromZone(game.getOpponentId(playable.card.controller), Hand, Predicates::isUnit, hasUnit?1:0, hasUnit?1:0, "Select a unit card from opponent's hand.");
                    discardedCard.forEach(cardId -> game.discard(game.getOpponentId(playable.card.controller), cardId));
                });
    }
}
