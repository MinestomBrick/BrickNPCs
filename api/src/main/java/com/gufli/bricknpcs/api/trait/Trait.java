package com.gufli.bricknpcs.api.trait;

public interface Trait {

    // entity lifecycle

    default void tick() {}

    default void onSpawn() {}

    default void onRemove() {}

    // trait lifecycle

    default void onEnable() {}

    default void onDisable() {}

}
