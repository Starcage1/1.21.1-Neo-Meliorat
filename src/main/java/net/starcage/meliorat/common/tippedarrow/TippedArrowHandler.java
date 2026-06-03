package net.starcage.meliorat.common.tippedarrow;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.config.MelioratConfig;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class TippedArrowHandler {

    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event) {

        if (!MelioratConfig.ENABLE_TIPPED_ARROWS.get())
            return;

        if (!(event.getProjectile() instanceof Arrow arrow))
            return;

        if (!(event.getRayTraceResult() instanceof net.minecraft.world.phys.BlockHitResult hit))
            return;

        ItemStack stack = arrow.getPickupItemStackOrigin();

        PotionContents contents =
                stack.getOrDefault(
                        DataComponents.POTION_CONTENTS,
                        PotionContents.EMPTY
                );

        if(contents.equals(PotionContents.EMPTY))
            return;

        AreaEffectCloud cloud =
                new AreaEffectCloud(
                        arrow.level(),
                        hit.getLocation().x,
                        hit.getLocation().y,
                        hit.getLocation().z
                );

        cloud.setRadius(
                MelioratConfig.ARROW_RADIUS.get().floatValue()
        );

        cloud.setDuration(
                MelioratConfig.ARROW_DURATION.get() * 20
        );

        contents.getAllEffects()
                .forEach(cloud::addEffect);

        arrow.level().addFreshEntity(cloud);

        arrow.discard();
    }
}