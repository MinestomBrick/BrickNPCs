package com.gufli.bricknpcs.api.traits;

import com.gufli.bricknpcs.api.npc.NPC;
import com.gufli.bricknpcs.api.trait.Trait;
import com.gufli.bricknpcs.api.trait.TraitFactory;
import com.gufli.pathfinding.minestom.MinestomNavigator;
import com.gufli.pathfinding.pathfinder.VectorPath;
import com.gufli.pathfinding.pathfinder.math.Vector;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.PacketUtils;
import net.minestom.server.utils.entity.EntityFinder;

import java.time.Instant;

public class FollowClosestPlayerTrait extends Trait {

    public static final TraitFactory FACTORY = FollowClosestPlayerTrait::new;

    private static final EntityFinder entityFinder;

    static {
        entityFinder = new EntityFinder();
        entityFinder.setTargetSelector(EntityFinder.TargetSelector.NEAREST_PLAYER);
    }

    //

    private Entity target;
    private MinestomNavigator pathFinder;

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
        this.pathFinder = new MinestomNavigator(npc.entity());
    }

    @Override
    public void tick() {
        if (target == null || target.isRemoved()) {
            target = findTarget();
            if (target == null) {
                return;
            }
        }

        if (npc.entity().getPosition().distance(target.getPosition()) > 40) {
            pathFinder.reset();
            this.target = null;
            return;
        }

        pathFinder.update();
        npc.entity().lookAt(target);

        if (pathFinder.currentPath() != null) {
            VectorPath vp = (VectorPath) pathFinder.currentPath();
//            for (Vector vec : vp.path() ) {
            Vector vec = vp.currentVector();
            PacketUtils.broadcastPacket(ParticleCreator.createParticlePacket(Particle.CRIT,
                    vec.blockX() + .5, vec.blockY() + .5, vec.blockZ() + .5,
                    0, 0, 0, 1));
//            }
        }

        if (previousTargetPosition != null
                && previousTargetPosition.blockX() == target.getPosition().blockX()
                && previousTargetPosition.blockY() == target.getPosition().blockY()
                && previousTargetPosition.blockZ() == target.getPosition().blockZ()
        ) {
            return;
        }

        this.previousTargetPosition = target.getPosition();
        pathFinder.pathTo(target.getPosition());
//        npc.entity().getNavigator().setPathTo(target.getPosition());
    }

}
