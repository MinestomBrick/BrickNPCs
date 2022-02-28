package org.minestombrick.npcs.api.trait;

import org.minestombrick.npcs.api.npc.NPC;

public abstract class Trait {

    protected final NPC npc;
    String name;

    protected Trait(NPC npc) {
        this.npc = npc;
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
