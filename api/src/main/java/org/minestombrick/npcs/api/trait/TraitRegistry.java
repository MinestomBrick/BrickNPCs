package org.minestombrick.npcs.api.trait;

import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.api.traits.FollowClosestPlayerTrait;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TraitRegistry {

    private static final Map<String, TraitFactory> traits = new ConcurrentHashMap<>();
    static {
        registerTrait("FollowClosestPlayerTrait", FollowClosestPlayerTrait.FACTORY);
    }

    public static void registerTrait(String name, TraitFactory factory) {
        traits.put(name, factory);
    }

    public static void unregisterTrait(String name) {
        traits.remove(name);
    }

    public static Collection<String> traits() {
        return Collections.unmodifiableSet(traits.keySet());
    }

    public static Optional<TraitFactory> trait(String name) {
        if ( !traits.containsKey(name) ) {
            return Optional.empty();
        }

        TraitFactory factory = traits.get(name);
        return Optional.of(npc -> {
            Trait trait = factory.create(npc);
            trait.name = name;
            return trait;
        });
    }

    public static Collection<TraitFactory> factories(NPCTemplate template) {
        Set<TraitFactory> result = new HashSet<>();

        for ( String name : template.traits() ) {
            if ( !traits.containsKey(name) ) {
                continue;
            }

            TraitFactory factory = traits.get(name);
            if ( !factory.appliesTo(template.type()) ) {
                continue;
            }

            // proxy factory to apply name
            result.add(npc -> {
                Trait trait = factory.create(npc);
                trait.name = name;
                return trait;
            });
        }

        return result;
    }

}
