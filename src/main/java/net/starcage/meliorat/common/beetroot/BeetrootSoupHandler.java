package net.starcage.meliorat.common.beetroot;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.config.MelioratConfig;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class BeetrootSoupHandler {

    @SubscribeEvent
    public static void onFinishUsingItem(
            LivingEntityUseItemEvent.Finish event
    ) {

        if (!MelioratConfig.ENABLE_BEETROOT_SOUP.get())
            return;

        if (!(event.getEntity() instanceof Player player))
            return;

        if (!event.getItem().is(Items.BEETROOT_SOUP))
            return;

        player.addEffect(
                new MobEffectInstance(
                        MobEffects.SATURATION,
                        100,
                        0
                )
        );
    }
}
