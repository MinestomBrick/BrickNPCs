package com.gufli.bricknpcs.api.trait;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;

@FunctionalInterface
public interface TraitFactory {

    Trait create(LivingEntity entity);

    default boolean appliesTo(EntityType type) {
        return true;
    }

}
