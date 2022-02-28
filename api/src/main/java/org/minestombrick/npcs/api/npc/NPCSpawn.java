package org.minestombrick.npcs.api.npc;

import net.minestom.server.coordinate.Pos;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface NPCSpawn {

    UUID id();

    @NotNull String name();

    @NotNull NPCTemplate template();

    void setTemplate(@NotNull NPCTemplate template);

    @NotNull Pos position();

    void setPosition(@NotNull Pos position);
}
