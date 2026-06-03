package net.starcage.meliorat.common.clock.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.common.clock.block.ClockBlock;
import net.starcage.meliorat.config.MelioratConfig;
import net.starcage.meliorat.registry.ModBlocks;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class ClockPlacementHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!MelioratConfig.ENABLE_CLOCK.get())
            return;
        ItemStack stack = event.getItemStack();
        Level level = event.getLevel();
        BlockPos clickedPos = event.getPos();
        BlockPos placePos = clickedPos.above();
        Player player = event.getEntity();

        if (!stack.is(Items.CLOCK)) {
            return;
        }

        if (event.getFace() != Direction.UP) {
            return;
        }

        long time = level.getDayTime() % 24000;

        int frame;

        if (time < 6000)
            frame = 0;
        else if (time < 12000)
            frame = 1;
        else if (time < 18000)
            frame = 2;
        else
            frame = 3;

        BlockState state =
                ModBlocks.CLOCK.get()
                        .defaultBlockState()
                        .setValue(
                                ClockBlock.FACING,
                                player.getDirection().getOpposite()
                        )
                        .setValue(
                                ClockBlock.TIME,
                                frame
                        );

        level.setBlock(placePos, state, 3);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);


    }
}
