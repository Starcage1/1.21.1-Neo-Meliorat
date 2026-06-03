package net.starcage.meliorat.common.cauldron;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.config.MelioratConfig;
import net.starcage.meliorat.registry.ModBlocks;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class CauldronHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

        if (!MelioratConfig.ENABLE_CAULDRON.get())
            return;

        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();

        BlockState state = level.getBlockState(pos);

        handleHoney(event, stack, player, level, pos, state);
        handleSlime(event, stack, player, level, pos, state);
    }

    private static void handleHoney(
            PlayerInteractEvent.RightClickBlock event,
            ItemStack stack,
            Player player,
            Level level,
            BlockPos pos,
            BlockState state) {

        if (!stack.is(Items.HONEY_BOTTLE))
            return;

        if (!(state.is(Blocks.CAULDRON) || state.is(ModBlocks.HONEY_CAULDRON.get())))
            return;

        int currentLevel = 0;

        if (state.is(ModBlocks.HONEY_CAULDRON.get())) {
            currentLevel = state.getValue(LayeredCauldronBlock.LEVEL);
        }

        currentLevel++;

        if(currentLevel >=
                MelioratConfig.HONEY_REQUIRED.get()) {

            level.setBlock(
                    pos,
                    Blocks.CAULDRON.defaultBlockState(),
                    3
            );

            if (!level.isClientSide()) {
                player.addItem(
                        new ItemStack(Blocks.HONEY_BLOCK)
                );
            }
        }
        else {

            level.setBlock(
                    pos,
                    ModBlocks.HONEY_CAULDRON.get()
                            .defaultBlockState()
                            .setValue(
                                    LayeredCauldronBlock.LEVEL,
                                    currentLevel
                            ),
                    3
            );
        }

        if (!player.getAbilities().instabuild) {

            stack.shrink(1);

            player.addItem(
                    new ItemStack(Items.GLASS_BOTTLE)
            );
        }

        event.setCanceled(true);
        event.setCancellationResult(
                InteractionResult.SUCCESS
        );
    }

    private static void handleSlime(
            PlayerInteractEvent.RightClickBlock event,
            ItemStack stack,
            Player player,
            Level level,
            BlockPos pos,
            BlockState state) {

        if (!stack.is(Items.SLIME_BALL))
            return;

        if (!(state.is(Blocks.CAULDRON) || state.is(ModBlocks.SLIME_CAULDRON.get())))
            return;

        int currentLevel = 0;

        if (state.is(ModBlocks.SLIME_CAULDRON.get())) {
            currentLevel = state.getValue(LayeredCauldronBlock.LEVEL);
        }

        currentLevel++;

        if(currentLevel >=
                MelioratConfig.SLIME_REQUIRED.get()) {

            level.setBlock(
                    pos,
                    Blocks.CAULDRON.defaultBlockState(),
                    3
            );

            if (!level.isClientSide()) {
                player.addItem(
                        new ItemStack(Blocks.SLIME_BLOCK)
                );
            }
        }
        else {

            level.setBlock(
                    pos,
                    ModBlocks.SLIME_CAULDRON.get()
                            .defaultBlockState()
                            .setValue(
                                    LayeredCauldronBlock.LEVEL,
                                    currentLevel
                            ),
                    3
            );
        }

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        event.setCanceled(true);
        event.setCancellationResult(
                InteractionResult.SUCCESS
        );
    }
}