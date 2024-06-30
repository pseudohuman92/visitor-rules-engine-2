/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.sets.set1;

import com.visitor.card.types.Asset;
import com.visitor.card.types.helpers.AbilityCard;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.protocol.Types;

import java.util.UUID;

import static com.visitor.protocol.Types.Counter.CHARGE;
import static com.visitor.protocol.Types.Knowledge.RED;

/**
 * @author pseudo
 */
public class MortarTeam extends Asset {

    int x;

    public MortarTeam(String owner) {
        super("Mortar Team", 1, new Hashmap(RED, 1),
                "Charge X.\n" +
                        "1, Activate, Sacrifice:\n" +
                        "  Deal X damage, X = # of charge counters.", owner);
    }

    @Override
    protected void beforePlay(Game game) {
        x = game.selectX(controller, game.getEnergy(controller) - 1);
        game.spendEnergy(controller, x);
        text = "Charge " + x + ".\n" +
                "\n" +
                "Dischage 1, Activate:\n" +
                "  Opponent purges 2.";
    }

    @Override
    protected void beforeResolve(Game game) {
        addCounters(CHARGE, x);
        text = "Charge X.\n" +
                "\n" +
                "Dischage 1, Activate:\n" +
                "  Opponent purges 2.";
    }

    @Override
    public boolean canActivateAdditional(Game game) {
        return game.hasEnergy(controller, 1);
    }

    @Override
    public void activate(Game game) {
        game.spendEnergy(controller, 1);
        game.sacrifice(id);
        int c = removeAllCounters(CHARGE);
        UUID target = game.selectDamageTargets(controller, 1, false).get(0);
        game.addToStack(new AbilityCard(this,
                "Deal " + c + " damage",
                (x) -> {
                    game.dealDamage(id, target, c);
                })
        );
    }

    @Override
    public Types.Card.Builder toCardMessage() {
        return super.toCardMessage().setCost("X+1");
    }
}
