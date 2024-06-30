package com.visitor.helpers.containers;

public class Damage {
    public int amount;
    public boolean mayKill;
    public boolean combat;

    public Damage(int amount, boolean mayKill, boolean combat) {
        this.amount = amount;
        this.mayKill = mayKill;
        this.combat = combat;
    }

    public Damage(int amount) {
        this(amount, true, false);
    }

}
