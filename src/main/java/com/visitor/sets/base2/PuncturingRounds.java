package com.visitor.sets.base2;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Cantrip;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;

import java.util.UUID;

import static com.visitor.protocol.Types.Knowledge.RED;

public class PuncturingRounds extends Cantrip {
    public PuncturingRounds(Game game, UUID owner) {
        super(game, "Puncturing Rounds", 1,
                new CounterMap<>(RED, 1),
                "Target unit you control gains +3/+0 and Trample until end of turn.",
                owner);

        playable.addTargetSingleUnit(Base.Zone.Play, null, t -> {
           game.addAttackAndHealth(t, 3, 0, true);
           game.addCombatAbility(t, Combat.CombatAbility.Trample, true);
        }, null, false);
    }
}
