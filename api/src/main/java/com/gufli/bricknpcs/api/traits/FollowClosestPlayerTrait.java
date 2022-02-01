package com.gufli.bricknpcs.api.traits;

import com.extollit.gaming.ai.path.HydrazinePathFinder;
import com.extollit.gaming.ai.path.SchedulingPriority;
import com.gufli.bricknpcs.api.trait.Trait;
import com.gufli.bricknpcs.api.trait.TraitFactory;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.ai.GoalSelector;
import net.minestom.server.entity.ai.TargetSelector;
import net.minestom.server.entity.ai.goal.FollowTargetGoal;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FollowClosestPlayerTrait extends Trait {

    public static final TraitFactory FACTORY = FollowClosestPlayerTrait::new;

    private static final EntityFinder entityFinder;

    static {
        entityFinder = new EntityFinder();
        entityFinder.setTargetSelector(EntityFinder.TargetSelector.NEAREST_PLAYER);
    }

    protected FollowClosestPlayerTrait(LivingEntity entity) {
        super(entity);
        if (!(entity instanceof EntityCreature)) {
            throw new IllegalArgumentException("Entity must be a creature.");
        }
    }

    @Override
    public void onEnable() {
        EntityCreature creature = (EntityCreature) entity;

        System.out.println("oi");
        creature.addAIGroup(
                List.of(new FollowGoal(creature)),
                List.of(new ClosestPlayerTargetSelector(creature))
        );

        List.of(new FollowTargetGoal(creature, Duration.of(2, ChronoUnit.SECONDS)));
    }

    @Override
    public void onDisable() {
        EntityCreature creature = (EntityCreature) entity;
        // TODO remove AI
    }

    private static class ClosestPlayerTargetSelector extends TargetSelector {

        public ClosestPlayerTargetSelector(@NotNull EntityCreature entityCreature) {
            super(entityCreature);
        }

        @Override
        public @Nullable Entity findTarget() {
            return entityFinder.findFirstPlayer(entityCreature.getInstance(), entityCreature);
        }
    }

    private static class FollowGoal extends GoalSelector {

        private Entity target;
        private Pos lastTargetPosition;
        private long totalTime;

//        private final HydrazinePathFinder pathFinder;

        public FollowGoal(@NotNull EntityCreature entityCreature) {
            super(entityCreature);
//            pathFinder = new HydrazinePathFinder(entityCreature.getNavigator().getPathingEntity(), entityCreature.getInstance().getInstanceSpace());
        }

        @Override
        public boolean shouldStart() {
            Entity target = entityCreature.getTarget();
            if (target == null) target = findTarget();
            if (target == null) return false;
            final boolean result = target.getPosition().distance(entityCreature.getPosition()) >= 2;
            if (result) {
                this.target = target;
            }
            return result;
        }

        @Override
        public void start() {
//            entityCreature.getNavigator().setPathFinder(pathFinder);
        }

        @Override
        public void tick(long deltaTime) {
            if (target == null) {
                return;
            }

            totalTime += deltaTime;
            if ( totalTime < 200 ) {
                return;
            }
            totalTime = 0;

            if (lastTargetPosition != null && target.getPosition().sameBlock(lastTargetPosition)) {
                return;
            }

            lastTargetPosition = target.getPosition();

            if (entityCreature.getPosition().distance(lastTargetPosition) <= 2) {
                entityCreature.getNavigator().setPathTo(null);
                return;
            }

            entityCreature.getNavigator().setPathTo(lastTargetPosition.add(0, 1, 0));
        }

        @Override
        public boolean shouldEnd() {
            return target == null || target.isRemoved()
                    || target.getPosition().distance(entityCreature.getPosition()) < 2;
        }

        @Override
        public void end() {
            this.entityCreature.getNavigator().setPathTo(null);
        }

    }
}
