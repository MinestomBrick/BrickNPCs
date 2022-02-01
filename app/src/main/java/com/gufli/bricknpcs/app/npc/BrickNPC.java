package com.gufli.bricknpcs.app.npc;

import com.gufli.bricknpcs.api.npc.NPC;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.api.trait.Trait;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.player.FakePlayerConnection;

import java.util.*;

public class BrickNPC implements NPC {

    private final Entity entity;

    private final NPCSpawn spawn;

    private final Set<Trait> traits = new HashSet<>();

    public BrickNPC(Instance instance, NPCSpawn spawn) {
        this.spawn = spawn;

        if (spawn.template().type() == EntityType.PLAYER ) {
            this.entity = new Player(UUID.randomUUID(), "", new FakePlayerConnection());
        } else {
            this.entity = new EntityCreature(spawn.template().type());
        }

        // TODO load traits from template

        traits.forEach(Trait::onCreate);

        entity.setAutoViewable(true);
        entity.setInstance(instance);
        entity.teleport(this.spawn.position());
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

    @Override
    public void refresh() {
        NPCTemplate template = spawn.template();

        if ( template.customName() != null ) {
            entity.setCustomName(template.customName());
            entity.setCustomNameVisible(true);
        }

        if ( entity instanceof Player player ) {
            if ( template.skin() != null ) {
                player.setSkin(template.skin());
            }
        }
    }
}
