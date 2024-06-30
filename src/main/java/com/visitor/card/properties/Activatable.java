/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.card.properties;

import com.visitor.game.Card;
import com.visitor.game.parts.Base;
import com.visitor.game.parts.Game;
import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.containers.ActivatedAbility;
import com.visitor.protocol.Types;

import java.util.Arrays;
import java.util.UUID;

/**
 * Interface for cards that has an activating ability.
 *
 * @author pseudo
 */
public class Activatable {

    private final Card card;
    private final Game game;

    private final Hashmap<UUID, ActivatedAbility> abilityList;
    private final Hashmap<UUID, ActivatedAbility> turnlyAbilityList;


    // Constructors
    public Activatable(Game game, Card card) {
        this.game = game;
        this.card = card;
        abilityList = new Hashmap<>();
        turnlyAbilityList = new Hashmap<>();
    }

    public final boolean canActivate() {
        for (ActivatedAbility activatedAbility : abilityList.values()) {
            if (activatedAbility.canActivate())
                return card.zone == Base.Zone.Play;
        }
        for (ActivatedAbility activatedAbility : turnlyAbilityList.values()) {
            if (activatedAbility.canActivate())
                return card.zone == Base.Zone.Play;
        }
        return false;
    }

    public final Arraylist<ActivatedAbility> getActivatableAbilities() {
        Arraylist<ActivatedAbility> abilities = new Arraylist<>();
        for (ActivatedAbility activatedAbility : abilityList.values()) {
            if (activatedAbility.canActivate())
                abilities.add(activatedAbility);
        }
        for (ActivatedAbility activatedAbility : turnlyAbilityList.values()) {
            if (activatedAbility.canActivate())
                abilities.add(activatedAbility);
        }
        return abilities;
    }

    public final ActivatedAbility getActivatableAbility(UUID abilityId) {
       return abilityList.get(abilityId) != null ? abilityList.get(abilityId) : turnlyAbilityList.get(abilityId);
    }


    public final void activate(UUID abilityId) {
        getActivatableAbility(abilityId).activate();
    }

    // Adders
    public Activatable addActivatedAbility(ActivatedAbility... abilities) {
        Arrays.asList(abilities).forEach(a -> abilityList.putIn(a.id, a));
        return this;
    }

    public Activatable addTurnlyActivatedAbility(ActivatedAbility... abilities) {
        Arrays.asList(abilities).forEach(a -> turnlyAbilityList.putIn(a.id, a));
        return this;
    }

    public void endTurn() {
        turnlyAbilityList.clear();
    }

    public Arraylist<Types.TargetingAbility> getAbilityBuilders() {
        Arraylist<Types.TargetingAbility> builders = new Arraylist<>();
        abilityList.values().forEach(a -> {
            if (a.canActivate()) {
                builders.add(a.getTargetingAbility());
            }
        });
        turnlyAbilityList.values().forEach(a -> {
            if (a.canActivate()) {
                builders.add(a.getTargetingAbility());
            }
        });
        return builders;
    }

    public void clear() {
        for (ActivatedAbility a : abilityList.values()){
            a.clear();
        }
        for (ActivatedAbility a : turnlyAbilityList.values()){
            a.clear();
        }
    }

    public void setAbilityTargets(UUID abilityId, Arraylist<Types.TargetSelection> targets) {
        ActivatedAbility a = abilityList.get(abilityId);
        if (a != null){
            a.setTargets(targets);
        } else {
            a = turnlyAbilityList.get(abilityId);
            if (a != null){
                a.setTargets(targets);
            }
        }
    }

    public Arraylist<UUID> getAllTargets() {
        Arraylist<UUID> targets = new Arraylist<>();
        for (ActivatedAbility t : abilityList.values()){
            targets.addAll(t.getAllTargets());
        }
        for (ActivatedAbility t : turnlyAbilityList.values()){
            targets.addAll(t.getAllTargets());
        }
        return targets;
    }
}
