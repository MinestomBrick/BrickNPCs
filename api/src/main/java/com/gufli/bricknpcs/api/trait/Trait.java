package com.gufli.bricknpcs.api.trait;

import net.minestom.server.entity.LivingEntity;

public abstract class Trait {

    protected final LivingEntity entity;
    String name;

    protected Trait(LivingEntity entity) {
        this.entity = entity;
    }

    public final String name() {
        return name;
    }

    // entity lifecycle

    public void tick() {}

    public void onSpawn() {}

    public void onRemove() {}

    // trait lifecycle

    public void onEnable() {}

    public void onDisable() {}

}
