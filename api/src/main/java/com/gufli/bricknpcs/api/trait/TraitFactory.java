package com.gufli.bricknpcs.api.trait;

import com.gufli.bricknpcs.api.npc.NPC;
import net.minestom.server.entity.EntityType;

@FunctionalInterface
public interface TraitFactory {

    Trait create(NPC npc);

    default boolean appliesTo(EntityType type) {
        return true;
    }

}
