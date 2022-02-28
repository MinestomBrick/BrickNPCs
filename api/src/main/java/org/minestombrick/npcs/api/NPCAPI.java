package org.minestombrick.npcs.api;

public class NPCAPI {

    private static NPCManager npcManager;

    public static void setNpcManager(NPCManager manager) {
        npcManager = manager;
    }

    //

    public static NPCManager get() {
        return npcManager;
    }

}
