package net.starcage.meliorat.common.ocelot;

import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.config.MelioratConfig;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class OcelotGuardianHandler {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {

        if (!MelioratConfig.ENABLE_OCELOT_GUARDIAN.get())
            return;

        if (event.getLevel().isClientSide()) {
            return;
        }

        if (event.getEntity() instanceof Zombie zombie) {
            zombie.goalSelector.addGoal(
                    1,
                    new AvoidEntityGoal<>(
                            zombie,
                            Ocelot.class,
                            8.0F,
                            1.0D,
                            1.2D
                    )
            );
        }

        if (event.getEntity() instanceof ZombieVillager zombieVillager) {
            zombieVillager.goalSelector.addGoal(
                    1,
                    new AvoidEntityGoal<>(
                            zombieVillager,
                            Ocelot.class,
                            8.0F,
                            1.0D,
                            1.2D
                    )
            );
        }

        if (event.getEntity() instanceof Husk husk) {
            husk.goalSelector.addGoal(
                    1,
                    new AvoidEntityGoal<>(
                            husk,
                            Ocelot.class,
                            8.0F,
                            1.0D,
                            1.2D
                    )
            );
        }

        if (event.getEntity() instanceof Drowned drowned) {
            drowned.goalSelector.addGoal(
                    1,
                    new AvoidEntityGoal<>(
                            drowned,
                            Ocelot.class,
                            8.0F,
                            1.0D,
                            1.2D
                    )
            );
        }

        if (event.getEntity() instanceof Spider spider) {
            spider.goalSelector.addGoal(
                    1,
                    new AvoidEntityGoal<>(
                            spider,
                            Ocelot.class,
                            8.0F,
                            1.0D,
                            1.2D
                    )
            );
        }

        if (event.getEntity() instanceof CaveSpider caveSpider) {
            caveSpider.goalSelector.addGoal(
                    1,
                    new AvoidEntityGoal<>(
                            caveSpider,
                            Ocelot.class,
                            8.0F,
                            1.0D,
                            1.2D
                    )
            );
        }

        if (event.getEntity() instanceof Creeper creeper) {
            creeper.goalSelector.addGoal(
                    1,
                    new AvoidEntityGoal<>(
                            creeper,
                            Ocelot.class,
                            8.0F,
                            1.0D,
                            1.2D
                    )
            );
        }
    }
}