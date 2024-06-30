package com.visitor.sets.token;

import com.visitor.card.properties.Combat;
import com.visitor.card.types.Unit;
import com.visitor.game.parts.Game;
import com.visitor.helpers.CounterMap;
import com.visitor.protocol.Types;

import java.util.UUID;

import static com.visitor.card.properties.Combat.CombatAbility.*;
import static com.visitor.protocol.Types.Knowledge.*;

public class UnitToken extends Unit {
    public UnitToken(Game game, String name, CardSubtype subtype, int cost, CounterMap<Types.Knowledge> knowledge, String text, int attack, int health, UUID owner, Combat.CombatAbility... combatAbilities) {
        super(game, name, cost, knowledge, text, attack, health, owner, combatAbilities);
        subtypes.add(subtype);
    }

    public static UnitToken Zombie_2_2(Game game, UUID owner) {
        return new UnitToken(game, "Zombie", CardSubtype.Zombie, 2, new CounterMap<>(PURPLE, 1), "", 2, 2, owner, Decay);
    }

    public static UnitToken Bat_1_1(Game game, UUID owner) {
        return new UnitToken(game, "Bat", CardSubtype.Bat, 1, new CounterMap<>(PURPLE, 1), "", 1, 1, owner, Evasive);
    }

    public static UnitToken Spirit_1_1(Game game, UUID owner) {
        return new UnitToken(game, "Spirit", CardSubtype.Spirit, 1, new CounterMap<>(YELLOW, 1), "", 1, 1, owner, Evasive);
    }

    public static UnitToken Drone_1_1(Game game, UUID owner) {
        return new UnitToken(game, "Drone", CardSubtype.Drone, 1, new CounterMap<>(YELLOW, 1), "", 1, 1, owner, Evasive);
    }

    public static UnitToken Warrior_1_1(Game game, UUID owner) {
        return new UnitToken(game, "Warrior", CardSubtype.Warrior, 1, new CounterMap<>(YELLOW, 1), "", 1, 1, owner);
    }

    public static UnitToken Golem_3_3(Game game, UUID owner) {
        return new UnitToken(game, "Golem", CardSubtype.Golem, 3, new CounterMap<>(), "", 3, 3, owner);
    }

    public static UnitToken Elf_1_1(Game game, UUID owner) {
        return new UnitToken(game, "Elf", CardSubtype.Elf, 1, new CounterMap<>(GREEN, 1), "", 1, 1, owner);
    }

    public static UnitToken Insect_1_1(Game game, UUID owner) {
        return new UnitToken(game, "Insect", CardSubtype.Insect, 1, new CounterMap<>(GREEN, 1).add(PURPLE, 1), "", 1, 1, owner, Deadly);
    }

    public static UnitToken Plant_1_1(Game game, UUID owner) {
        return new UnitToken(game, "Plant", CardSubtype.Plant, 1, new CounterMap<>(GREEN, 1), "", 1, 1, owner);
    }

    public static UnitToken Wurm_5_5(Game game, UUID owner) {
        return new UnitToken(game, "Wurm", CardSubtype.Wurm, 5, new CounterMap<>(GREEN, 2), "", 5, 5, owner, Trample);
    }

    public static UnitToken Wolf_3_3(Game game, UUID owner) {
        return new UnitToken(game, "Wolf", CardSubtype.Wolf, 3, new CounterMap<>(GREEN, 1).add(YELLOW, 1), "", 3, 3, owner);
    }
}


