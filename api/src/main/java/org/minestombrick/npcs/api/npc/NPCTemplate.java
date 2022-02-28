package org.minestombrick.npcs.api.npc;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.PlayerSkin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface NPCTemplate {

    UUID id();

    @NotNull String name();

    @Nullable Component customName();

    void setCustomName(@NotNull Component name);

    @NotNull EntityType type();

    void setType(@NotNull EntityType type);

    /**
     * Get the player skin if the type is EntityType.PLAYER
     */
    @Nullable PlayerSkin skin();

    /**
     * Set the player skin if the type is EntityType.PLAYER
     */
    void setSkin(@NotNull PlayerSkin skin);


    void addTrait(@NotNull String name);

    void removeTrait(@NotNull String name);

    @NotNull Collection<String> traits();
}
