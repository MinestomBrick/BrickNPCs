package org.minestombrick.npcs.app.npc;

import org.minestombrick.npcs.api.npc.NPC;
import org.minestombrick.npcs.api.npc.NPCSpawn;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.api.trait.Trait;
import org.minestombrick.npcs.api.trait.TraitRegistry;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.Instance;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BrickNPC implements NPC {

    protected final EntityCreature entity;
    protected final NPCSpawn spawn;
    protected final Set<Trait> traits = new HashSet<>();

    protected BrickNPC(NPCSpawn spawn, EntityCreature entity) {
        this.spawn = spawn;
        this.entity = entity;

        TraitRegistry.factories(spawn.template()).forEach(factory -> traits.add(factory.create(this)));

        entity.setAutoViewable(true);
        refresh();

        traits.forEach(Trait::onEnable);
        traits.forEach(Trait::onSpawn);
    }

    public BrickNPC(Instance instance, NPCSpawn spawn) {
        this(spawn, create(instance, spawn.template().type()));
    }

    private static EntityCreature create(Instance instance, EntityType type) {
        EntityCreature entity = new EntityCreature(type);
        entity.setInstance(instance);
        return entity;
    }

    @Override
    public EntityCreature entity() {
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
        traits.forEach(Trait::onDisable);
        traits.clear();
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

        Collection<String> templateTraits = template.traits();
        traits.stream().filter(trait -> !templateTraits.contains(trait.name())).forEach(trait -> {
            trait.onDisable();
            traits.remove(trait);
        });

        templateTraits.stream().filter(name -> traits.stream().noneMatch(trait -> trait.name().equals(name)))
                .forEach(name -> TraitRegistry.trait(name).ifPresent(factory -> addTrait(factory.create(this))));
    }

    public void tick() {
        traits.forEach(Trait::tick);
    }
}
