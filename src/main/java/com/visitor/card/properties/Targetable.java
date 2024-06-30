package com.visitor.card.properties;

import com.visitor.game.parts.Base;

import java.util.UUID;

public interface Targetable {
    public UUID getId();
    public Base.Zone getZone();

    public boolean isDamagable();
}
