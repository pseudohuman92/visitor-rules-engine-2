package com.visitor.card.types;

import com.visitor.card.properties.Studiable;
import com.visitor.game.Card;
import com.visitor.game.Player;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types;
import com.visitor.protocol.Types.Knowledge;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.UUID;

/**
 * Abstract class for the Tome card type.
 *
 * @author pseudo
 */
public abstract class Tome extends Card {

    public Tome(Game game, String name, String text, UUID owner) {
        super(game, name, new CounterMap<>(), CardType.Tome, text, owner);

        studiable = new Studiable(game, this, (a,b,c) ->{}, true);
    }

    public Tome(Game game, String name, String text, UUID owner, TriConsumer<Player, Boolean, CounterMap<Knowledge>> additionalStudy) {
        this(game, name, text, owner);

        studiable.setAdditionalStudyEffect(additionalStudy);
    }

    public Tome(Game game, String name, String text, UUID owner, CounterMap<Knowledge> studyKnowledgeType) {
        this(game, name, text, owner);

        studiable.setAdditionalStudyEffect((a, b, c) -> game.addKnowledge(owner, studyKnowledgeType));
    }

    @Override
    public Types.CardP.Builder toCardMessage() {
        return super.toCardMessage()
                .setCost("");
    }
}