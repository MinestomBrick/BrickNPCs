package org.minestombrick.npcs.api.trait;

import org.minestombrick.npcs.api.npc.NPC;
import net.minestom.server.entity.EntityType;

@FunctionalInterface
public interface TraitFactory {

    Trait create(NPC npc);

    default boolean appliesTo(EntityType type) {
        return true;
    }

}
