package org.minestombrick.npcs.api;

import org.minestombrick.npcs.api.npc.NPC;
import org.minestombrick.npcs.api.npc.NPCSpawn;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface NPCManager {

    static NPCManager get() {
        return NPCAPI.get();
    }

    // templates

    Optional<NPCTemplate> template(@NotNull String name);

    Optional<NPCTemplate> template(@NotNull UUID id);

    Collection<NPCTemplate> templates();

    NPCTemplate newTemplate(@NotNull String name);

    CompletableFuture<Void> saveTemplate(@NotNull NPCTemplate template);

    CompletableFuture<Void> deleteTemplate(@NotNull NPCTemplate template);

    //

    Optional<NPCSpawn> spawn(@NotNull String name);

    Optional<NPCSpawn> spawn(@NotNull UUID id);

    Collection<NPCSpawn> spawns();

    NPCSpawn newSpawn(@NotNull String name, @NotNull Pos position, @NotNull NPCTemplate template);

    CompletableFuture<Void> saveSpawn(@NotNull NPCSpawn spawn);

    CompletableFuture<Void> deleteSpawn(@NotNull NPCSpawn spawn);

    //

    Collection<NPC> npcs();

    void remove(@NotNull NPC npc);

    NPC spawn(@NotNull Instance instance, @NotNull NPCSpawn spawn);

}
