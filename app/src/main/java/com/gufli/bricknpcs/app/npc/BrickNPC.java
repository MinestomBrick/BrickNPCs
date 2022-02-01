package com.gufli.bricknpcs.app.npc;

import com.gufli.bricknpcs.api.npc.NPC;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.api.trait.Trait;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.fakeplayer.FakePlayer;
import net.minestom.server.entity.fakeplayer.FakePlayerOption;
import net.minestom.server.entity.hologram.Hologram;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class BrickNPC implements NPC {

    protected Entity entity;
    protected final NPCSpawn spawn;
    protected final Set<Trait> traits = new HashSet<>();

    protected BrickNPC(NPCSpawn spawn) {
        this.spawn = spawn;
        // TODO load traits from template
    }

    public BrickNPC(Instance instance, NPCSpawn spawn) {
        this(spawn);

        this.entity = new EntityCreature(spawn.template().type());
        this.entity.setInstance(instance);

        entity.setAutoViewable(true);
        refresh();

        traits.forEach(Trait::onSpawn);
    }

    @Override
    public Entity entity() {
        return entity;
    }

    @Override
    public NPCSpawn spawn() {
        return spawn;
    }

    @Override
    public Collection<Trait> traits() {
        return Collections.unmodifiableSet(traits);
    }

    @Override
    public void addTrait(Trait trait) {
        traits.add(trait);
        trait.onEnable();
    }

    @Override
    public void removeTrait(Trait trait) {
        traits.remove(trait);
        trait.onDisable();
    }

    public void remove() {
        traits.forEach(Trait::onRemove);
        entity.remove();
    }

    @Override
    public void refresh() {
        NPCTemplate template = spawn.template();

        entity.teleport(spawn.position());

        if (template.customName() != null) {
            entity.setCustomName(template.customName());
            entity.setCustomNameVisible(true);
        }
    }
}
