package com.visitor.sets.base2;

import com.visitor.card.types.Ritual;
import com.visitor.game.Card;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.YELLOW;

public class Repurpose extends Ritual {

    int addShield = 0;
    public Repurpose(Game game, UUID owner) {
        super(game, "Repurpose", 3,
                new CounterMap<>(YELLOW, 1),
                "As an additional cost, purge a unit from your graveyard.\n Target unit you control gains shield X where X is equal to half of banished unit's max health, rounded down.",
                owner);

        //TODO: Probably needs fixing
        playable.addTargetSingleCard(Base.Zone.Discard_Pile, Predicates::isUnit, "Select a card to purge",
                cid -> {
                    Card c = game.getCard(cid);
                    addShield = c.getMaxHealth()/2;
                    game.purge(c.getId());
                },  true);

        playable.addTargetSingleCard(Base.Zone.Play, Predicates::isUnit, "Select a unit you control", cid -> game.addShield(cid,addShield, false), false);
    }
}
