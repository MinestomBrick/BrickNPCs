package com.gufli.bricknpcs.api.traits;

import com.extollit.gaming.ai.path.HydrazinePathFinder;
import com.gufli.bricknpcs.api.npc.NPC;
import com.gufli.bricknpcs.api.trait.Trait;
import com.gufli.bricknpcs.api.trait.TraitFactory;
import com.gufli.pathfinding.minestom.MinestomPathfinder;
import com.gufli.pathfinding.pathfinder.VectorPath;
import com.gufli.pathfinding.pathfinder.math.Vector;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.pathfinding.Navigator;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.PacketUtils;
import net.minestom.server.utils.entity.EntityFinder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class FollowClosestPlayerTrait extends Trait {

    public static final TraitFactory FACTORY = FollowClosestPlayerTrait::new;

    private static final EntityFinder entityFinder;

    static {
        entityFinder = new EntityFinder();
        entityFinder.setTargetSelector(EntityFinder.TargetSelector.NEAREST_PLAYER);
    }


    //

    private Entity target;
    private MinestomPathfinder pathFinder;

    private Pos previousTargetPosition;
    private Instant previous;

    protected FollowClosestPlayerTrait(NPC npc) {
        super(npc);
    }

    private Entity findTarget() {
        return entityFinder.findFirstPlayer(npc.entity().getInstance(), npc.entity());
    }

    @Override
    public void onEnable() {
        this.pathFinder = new MinestomPathfinder(npc.entity());
    }

    @Override
    public void tick() {
        if ( target == null ) {
            target = findTarget();
            if (target == null) {
                return;
            }
        }

        if (npc.entity().getPosition().distance(target.getPosition()) > 40 ) {
            pathFinder.reset();
            this.target = null;
            return;
        }

        pathFinder.update();
        npc.entity().lookAt(target);

        if ( pathFinder.currentPath() != null ) {
            VectorPath vp = (VectorPath) pathFinder.currentPath();
            for (Vector vec : vp.path() ) {
                PacketUtils.broadcastPacket(ParticleCreator.createParticlePacket(Particle.FLAME,
                        vec.x(), vec.y() + .5, vec.z(),
                        0, 0, 0, 1));
            }
        }

        if ( previousTargetPosition != null
                && previousTargetPosition.blockX() == target.getPosition().blockX()
                && previousTargetPosition.blockY() == target.getPosition().blockY()
                && previousTargetPosition.blockZ() == target.getPosition().blockZ()
        ) {
            return;
        }

        this.previousTargetPosition = target.getPosition();
        pathFinder.pathTo(target.getPosition());
    }

}
