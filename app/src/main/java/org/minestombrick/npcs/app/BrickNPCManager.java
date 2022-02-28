package org.minestombrick.npcs.app;

import org.minestombrick.npcs.api.NPCManager;
import org.minestombrick.npcs.api.npc.NPC;
import org.minestombrick.npcs.api.npc.NPCSpawn;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.app.data.BrickNPCsDatabaseContext;
import org.minestombrick.npcs.app.data.beans.BNPCSpawn;
import org.minestombrick.npcs.app.data.beans.BNPCTemplate;
import org.minestombrick.npcs.app.data.beans.query.QBNPCSpawn;
import org.minestombrick.npcs.app.data.beans.query.QBNPCTemplate;
import org.minestombrick.npcs.app.npc.BrickHumanNPC;
import org.minestombrick.npcs.app.npc.BrickNPC;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityType;
import net.minestom.server.event.instance.InstanceChunkLoadEvent;
import net.minestom.server.event.instance.InstanceChunkUnloadEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class BrickNPCManager implements NPCManager {

    private final BrickNPCsDatabaseContext databaseContext;

    private final Set<NPCTemplate> templates = new CopyOnWriteArraySet<>();
    private final Set<NPCSpawn> spawns = new CopyOnWriteArraySet<>();

    private final Set<BrickNPC> npcs = new CopyOnWriteArraySet<>();

    public BrickNPCManager(BrickNPCsDatabaseContext databaseContext) {
        this.databaseContext = databaseContext;

        templates.addAll(new QBNPCTemplate().findSet());

        new QBNPCSpawn().findSet().forEach(spawn -> {
            spawn.template = (BNPCTemplate) template(spawn.template.id()).orElse(null);
            spawns.add(spawn);
        });

        // load npc on chunk load
        MinecraftServer.getGlobalEventHandler().addListener(InstanceChunkLoadEvent.class, e -> {
            Set<NPCSpawn> spawns = this.spawns.stream()
                    .filter(spawn -> spawn.position().chunkX() == e.getChunkX()
                            && spawn.position().chunkZ() == e.getChunkZ())
                    .filter(spawn -> npcs.stream().noneMatch(npc -> npc.spawn() == spawn))
                    .collect(Collectors.toSet());

            MinecraftServer.getSchedulerManager()
                    .buildTask(() -> spawns.forEach(spawn -> spawn(e.getInstance(), spawn)))
                    .delay(Duration.of(1, ChronoUnit.SECONDS)).schedule();
        });

        // remove npcs on chunk unload
        MinecraftServer.getGlobalEventHandler().addListener(InstanceChunkUnloadEvent.class, e -> {
            npcs.stream().filter(npc -> npc.entity().getChunk() == e.getChunk()).forEach(this::remove);
        });

        // npc ticking
        MinecraftServer.getSchedulerManager()
                .buildTask(() -> npcs.forEach(BrickNPC::tick))
                .repeat(TaskSchedule.tick(1)).schedule();
    }

    //

    @Override
    public Optional<NPCTemplate> template(@NotNull String name) {
        return templates.stream().filter(template -> template.name().equals(name)).findFirst();
    }

    @Override
    public Optional<NPCTemplate> template(@NotNull UUID id) {
        return templates.stream().filter(template -> template.id().equals(id)).findFirst();
    }

    @Override
    public Collection<NPCTemplate> templates() {
        return Collections.unmodifiableSet(templates);
    }

    @Override
    public NPCTemplate newTemplate(@NotNull String name) {
        return new BNPCTemplate(name);
    }

    @Override
    public CompletableFuture<Void> saveTemplate(@NotNull NPCTemplate template) {
        if (template(template.name()).stream().anyMatch(temp -> temp != template)) {
            throw new IllegalArgumentException("A template with that name already exists.");
        }

        npcs.stream().filter(npc -> npc.spawn().template() == template).forEach(NPC::refresh);

        templates.add(template);
        return databaseContext.saveAsync((BNPCTemplate) template);
    }

    @Override
    public CompletableFuture<Void> deleteTemplate(@NotNull NPCTemplate template) {
        templates.remove(template);
        return databaseContext.deleteAsync((BNPCTemplate) template);
    }

    //

    @Override
    public Optional<NPCSpawn> spawn(@NotNull String name) {
        return spawns.stream().filter(spawn -> spawn.name().equals(name)).findFirst();
    }

    @Override
    public Optional<NPCSpawn> spawn(@NotNull UUID id) {
        return spawns.stream().filter(spawn -> spawn.id().equals(id)).findFirst();
    }

    @Override
    public Collection<NPCSpawn> spawns() {
        return Collections.unmodifiableSet(spawns);
    }

    @Override
    public NPCSpawn newSpawn(@NotNull String name, @NotNull Pos position, @NotNull NPCTemplate template) {
        return new BNPCSpawn(name, position, (BNPCTemplate) template);
    }

    @Override
    public CompletableFuture<Void> saveSpawn(@NotNull NPCSpawn spawn) {
        if (spawn(spawn.name()).stream().anyMatch(loc -> loc != spawn)) {
            throw new IllegalArgumentException("A spawn with that name already exists.");
        }

        npcs.stream().filter(npc -> npc.spawn() == spawn).forEach(NPC::refresh);

        spawns.add(spawn);
        return databaseContext.saveAsync((BNPCSpawn) spawn);
    }

    @Override
    public CompletableFuture<Void> deleteSpawn(@NotNull NPCSpawn spawn) {
        npcs.stream().filter(npc -> npc.spawn() == spawn).forEach(this::remove);

        spawns.remove(spawn);
        return databaseContext.deleteAsync((BNPCSpawn) spawn);
    }

    //

    @Override
    public Collection<NPC> npcs() {
        return Collections.unmodifiableSet(npcs);
    }

    @Override
    public void remove(@NotNull NPC npc) {
        BrickNPC bnpc = (BrickNPC) npc;

        npcs.remove(bnpc);
        bnpc.remove();
    }

    @Override
    public NPC spawn(@NotNull Instance instance, @NotNull NPCSpawn spawn) {
        BrickNPC npc;
        if ( spawn.template().type() == EntityType.PLAYER ) {
            npc = new BrickHumanNPC(instance, spawn);
        } else {
            npc = new BrickNPC(instance, spawn);
        }

        npcs.add(npc);
        return npc;
    }

    //


}
