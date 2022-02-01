package com.gufli.bricknpcs.api.npc;

import com.gufli.bricknpcs.api.trait.Trait;
import net.minestom.server.entity.Entity;

import java.util.Collection;

public interface NPC {

    Entity entity();

    NPCSpawn spawn();

    /**
     * This will apply the template to the entity
     */
    void refresh();

    Collection<Trait> traits();

    /**
     * This will only modify the current npc and not make any updates to the template
     */
    void addTrait(Trait trait);

    /**
     * This will only modify the current npc and not make any updates to the template
     */
    void removeTrait(Trait trait);

}
