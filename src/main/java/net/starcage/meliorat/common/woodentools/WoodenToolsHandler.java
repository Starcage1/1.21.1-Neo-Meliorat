package net.starcage.meliorat.common.woodentools;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.config.MelioratConfig;
import net.starcage.meliorat.mixin.CampfireBlockEntityAccessor;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class WoodenToolsHandler {

    @SubscribeEvent
    public static void onBlockBreak(
            BlockEvent.BreakEvent event
    ) {

        if (!MelioratConfig.ENABLE_WOODEN_TOOLS_IMPROVEMENTS.get())
            return;

        Player player = event.getPlayer();

        ItemStack tool =
                player.getMainHandItem();

        if (tool.is(Items.WOODEN_AXE)) {
            handleWoodAxe(event);
        }

        if (tool.is(Items.WOODEN_SHOVEL)) {
            handleWoodShovel(event);
        }
    }

    private static void handleWoodAxe(
            BlockEvent.BreakEvent event
    ) {

        var state = event.getState();

        if (!state.is(BlockTags.LOGS))
            return;

        if (Math.random() >
                MelioratConfig.WOOD_AXE_LOG_CHANCE.get()) {
            return;
        }

        BlockPos pos = event.getPos();

        if (state.getBlock().asItem() == Items.AIR)
            return;

        Block.popResource(
                (Level) event.getLevel(),
                pos,
                new ItemStack(
                        state.getBlock().asItem()
                )
        );
    }

    private static void handleWoodShovel(
            BlockEvent.BreakEvent event
    ) {

        if (!event.getState().is(Blocks.GRAVEL))
            return;

        double chance =
                0.10D +
                        MelioratConfig.WOOD_SHOVEL_FLINT_BONUS.get();

        if (Math.random() > chance)
            return;

        Block.popResource(
                (Level) event.getLevel(),
                event.getPos(),
                new ItemStack(
                        Items.FLINT
                )
        );
    }

    @SubscribeEvent
    public static void onComposterUse(
            PlayerInteractEvent.RightClickBlock event
    ) {

        if (!MelioratConfig.ENABLE_WOODEN_TOOLS_IMPROVEMENTS.get())
            return;

        if (event.getLevel().isClientSide())
            return;

        BlockPos pos = event.getPos();
        Level level = event.getLevel();

        BlockState state =
                level.getBlockState(pos);

        if (!state.is(Blocks.COMPOSTER))
            return;

        ItemStack compostItem =
                event.getItemStack();

        if (ComposterBlock.getValue(compostItem) <= 0)
            return;

        ItemStack hoe =
                event.getEntity()
                        .getOffhandItem();

        if (!hoe.is(Items.WOODEN_HOE))
            return;

        int oldLevel =
                state.getValue(
                        ComposterBlock.LEVEL
                );

        level.getServer().execute(() -> {

            BlockState newState =
                    level.getBlockState(pos);

            if (!newState.is(Blocks.COMPOSTER))
                return;

            int newLevel =
                    newState.getValue(
                            ComposterBlock.LEVEL
                    );

            if (newLevel <= oldLevel)
                return;

            if (Math.random() >
                    MelioratConfig.WOOD_HOE_COMPOST_CHANCE.get())
                return;

            if (newLevel >= 7)
                return;

            level.setBlock(
                    pos,
                    newState.setValue(
                            ComposterBlock.LEVEL,
                            newLevel + 1
                    ),
                    3
            );

            hoe.hurtAndBreak(
                    1,
                    (ServerPlayer) event.getEntity(),
                    net.minecraft.world.entity.EquipmentSlot.OFFHAND
            );
        });
    }

    @SubscribeEvent
    public static void onCampfireTend(
            PlayerInteractEvent.RightClickBlock event
    ) {

        if (!MelioratConfig.ENABLE_WOODEN_TOOLS_IMPROVEMENTS.get())
            return;

        if (event.getLevel().isClientSide())
            return;

        BlockState state =
                event.getLevel()
                        .getBlockState(event.getPos());

        if (!state.is(Blocks.CAMPFIRE))
            return;

        ItemStack tool =
                event.getItemStack();

        int boost = 0;

        if (tool.is(Items.WOODEN_SHOVEL))
            boost =
                    MelioratConfig
                            .WOODEN_SHOVEL_CAMPFIRE_BOOST
                            .get();

        if (tool.is(Items.WOODEN_HOE))
            boost =
                    MelioratConfig
                            .WOODEN_HOE_CAMPFIRE_BOOST
                            .get();

        if (boost == 0)
            return;

        if (!(event.getLevel().getBlockEntity(event.getPos())
                instanceof CampfireBlockEntity campfire))
            return;

        CampfireBlockEntityAccessor accessor =
                (CampfireBlockEntityAccessor) campfire;

        int[] progress =
                accessor.meliorat$getCookingProgress();

        int[] total =
                accessor.meliorat$getCookingTime();

        boolean changed = false;

        for (int i = 0; i < progress.length; i++) {

            if (campfire.getItems().get(i).isEmpty())
                continue;

            if (progress[i] >= total[i])
                continue;

            progress[i] =
                    Math.min(
                            progress[i] + boost,
                            total[i]
                    );

            changed = true;
        }

        if (!changed)
            return;

        tool.hurtAndBreak(
                1,
                (ServerPlayer) event.getEntity(),
                net.minecraft.world.entity.EquipmentSlot.MAINHAND
        );

        campfire.setChanged();

        event.getLevel().sendBlockUpdated(
                event.getPos(),
                state,
                state,
                3
        );

        event.getEntity()
                .getCooldowns()
                .addCooldown(
                        tool.getItem(),
                        10
                );

        event.setCanceled(true);
    }
}
