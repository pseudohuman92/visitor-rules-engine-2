/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.card.types.helpers;

import com.visitor.card.properties.Playable;
import com.visitor.game.Card;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.protocol.Types;

import java.util.UUID;

/**
 * @author pseudo
 * Abilities are represenbted as "cards" that do their effect on resolution then disappear.
 */


public class AbilityCard extends Card {

    Arraylist<UUID> targets;

    public AbilityCard(Game game, Card creator, String text, Runnable effect) {
        super(game, creator.name, new CounterMap<>(), CardType.Ability, text, creator.controller);
        playable = new Playable(game, this).addResolveEffect(effect).setDisappearing();
        targets = new Arraylist<>(creator.getId());
        targets.addAll(creator.getAllTargets());

    }

    public AbilityCard(Game game, Card creator, ActivatedAbility activatedAbility) {
        this(game, creator, activatedAbility.getText(), activatedAbility::runEffects);
        //setId(activatedAbility.id);
    }


    @Override
    public Types.CardP.Builder toCardMessage() {
        return super.toCardMessage()
                .setCost("")
                .addAllTargets(targets.transformToStringList());
    }

}
