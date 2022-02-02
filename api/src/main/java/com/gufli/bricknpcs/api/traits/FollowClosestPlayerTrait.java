package com.gufli.bricknpcs.api.traits;

import com.extollit.gaming.ai.path.HydrazinePathFinder;
import com.extollit.gaming.ai.path.model.IDynamicMovableObject;
import com.extollit.gaming.ai.path.model.INode;
import com.extollit.gaming.ai.path.model.IPath;
import com.extollit.linalg.immutable.Vec3d;
import com.gufli.bricknpcs.api.npc.NPC;
import com.gufli.bricknpcs.api.trait.Trait;
import com.gufli.bricknpcs.api.trait.TraitFactory;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.pathfinding.Navigator;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.PacketUtils;
import net.minestom.server.utils.entity.EntityFinder;

import java.lang.reflect.Field;
import java.time.Instant;

public class FollowClosestPlayerTrait extends Trait {

    public static final TraitFactory FACTORY = FollowClosestPlayerTrait::new;

    private static final EntityFinder entityFinder;

    static {
        entityFinder = new EntityFinder();
        entityFinder.setTargetSelector(EntityFinder.TargetSelector.NEAREST_PLAYER);
    }

    private static Field pathFinderField;
    static {
        try {
            pathFinderField = Navigator.class.getDeclaredField("pathFinder");
            pathFinderField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    //

    private Entity target;
    private IDynamicMovableObject targetObject;

    private HydrazinePathFinder pathFinder;

    protected FollowClosestPlayerTrait(NPC npc) {
        super(npc);
    }

    private Entity findTarget() {
        return entityFinder.findFirstPlayer(npc.entity().getInstance(), npc.entity());
    }

    @Override
    public void onEnable() {
        try {
            pathFinder = (HydrazinePathFinder) pathFinderField.get(npc.entity().getNavigator());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        if ( target != null ) {
            if (npc.entity().getPosition().distance(target.getPosition()) > 40 ) {
                pathFinder.reset();
                this.target = null;
                this.targetObject = null;
                return;
            }

            IPath path = pathFinder.updatePathFor(npc.entity().getNavigator().getPathingEntity());

            if ( path != null ) {
                for (INode node : path) {
                    PacketUtils.broadcastPacket(ParticleCreator.createParticlePacket(Particle.FLAME,
                            node.coordinates().x + .5, node.coordinates().y + .1, node.coordinates().z + .5,
                            0, 0, 0, 1));

                }
            }
            return;
        }

        target = findTarget();
        if ( target == null ) {
            return;
        }

        targetObject = new IDynamicMovableObject() {
            @Override
            public Vec3d coordinates() {
                return new Vec3d(target.getPosition().x(), target.getPosition().y(), target.getPosition().z());
            }

            @Override
            public float width() {
                return (float) target.getBoundingBox().getWidth();
            }

            @Override
            public float height() {
                return (float) target.getBoundingBox().getHeight();
            }
        };

        pathFinder.trackPathTo(targetObject);
    }

}
