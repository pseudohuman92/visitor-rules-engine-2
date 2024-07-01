/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.base2;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Asset;
import com.visitor.game.Card;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.CounterMap;
import com.visitor.helpers.Predicates;
import com.visitor.helpers.containers.ActivatedAbility;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.Vigilance;
import static com.visitor.protocol.Types.Knowledge.YELLOW;

/**
 * @author pseudo
 */
public class Reassembler extends Asset {

    private int totalAttack;
    private int totalHealth;
    private int totalShield;

    private final CounterMap<Combat.CombatAbility> totalCombatAbilities;

    public Reassembler(Game game, UUID owner) {
        super(game, "Reassembler",
                3, new CounterMap(YELLOW, 1),
                "{Use}: Purge one or more units from your discard pile: Target unit you control gains their total attack, health, shield and combat abilities.",
                owner);

        totalAttack = 0;
        totalHealth = 0;
        totalShield = 0;
        totalCombatAbilities = new CounterMap<>();

        activatable.addActivatedAbility(new ActivatedAbility(game, this, 0, "{Use}: Purge one or more units from your discard pile: Target unit you control gains their total attack, health, shield and combat abilities.")
                .addTargeting(Base.Zone.Discard_Pile, Predicates::isUnit, 1, 999, "Purge any number of units.", t -> {
                    totalAttack += game.getCard(t).getAttack();
                    totalHealth += game.getCard(t).getHealth();
                    totalShield += game.getCard(t).getShields();
                    totalCombatAbilities.merge(game.getCard(t).getCombatAbilities());
                    game.purge(t);
                    }, true
                )
                .addTargeting(Base.Zone.Play, Predicates::isUnit, 1, 1, "Target unit you control gains their total attack, health, shield and combat abilities.", id -> {
                    Card c = game.getCard(id);
                    c.addAttackAndHealth(totalAttack, totalHealth, false);
                    c.addShield(totalShield, false);
                    totalCombatAbilities.forEach(c::addCombatAbility);
                }, false));
    }

    public void clear(){
        super.clear();
        totalAttack = 0;
        totalHealth = 0;
        totalShield = 0;
        totalCombatAbilities.clear();
    }
}
