package net.starcage.meliorat.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.starcage.meliorat.config.MelioratConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.animal.Dolphin$DolphinSwimToTreasureGoal")
public class DolphinTreasureGoalMixin {

    @Shadow
    @Final
    private Dolphin dolphin;

    @Unique
    private boolean meliorat$reefPhase = false;

    @Unique
    private BlockPos meliorat$storedShipwreck;

    @Inject(
            method = "start",
            at = @At("TAIL")
    )
    private void meliorat$findReef(CallbackInfo ci) {

        if (!MelioratConfig.ENABLE_DOLPHIN_GUIDE.get())
            return;

        if (!(dolphin.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        // Second trip = vanilla shipwreck hunt
        if (meliorat$storedShipwreck != null && !meliorat$reefPhase) {

            dolphin.setTreasurePos(meliorat$storedShipwreck);

            return;
        }

        // First trip only
        if (meliorat$reefPhase) {
            return;
        }

        // Store vanilla shipwreck location
        meliorat$storedShipwreck = dolphin.getTreasurePos();

        Pair<BlockPos, Holder<Biome>> reef =
                serverLevel.findClosestBiome3d(
                        biome -> biome.is(Biomes.WARM_OCEAN),
                        dolphin.blockPosition(),
                        512,
                        32,
                        64
                );

        if (reef == null) {
            return;
        }

        BlockPos reefPos = reef.getFirst();

        BlockPos coralPos = findCoralNear(
                serverLevel,
                reefPos
        );

        if (coralPos != null) {
            reefPos = coralPos;
        }

        dolphin.setTreasurePos(reefPos);
        meliorat$reefPhase = true;

    }

    @Inject(
            method = "stop",
            at = @At("HEAD")
    )
    private void meliorat$continueToShipwreck(CallbackInfo ci) {

        if (!(dolphin.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        // Finished reef tour
        if (meliorat$reefPhase) {

            meliorat$reefPhase = false;

            dolphin.setTreasurePos(meliorat$storedShipwreck);

            dolphin.setGotFish(true);

        }
        else {

            // Finished shipwreck hunt
            meliorat$storedShipwreck = null;
        }
    }

    @Unique
    private BlockPos findCoralNear(ServerLevel level, BlockPos center) {

        for (int x = -64; x <= 64; x++) {
            for (int y = -16; y <= 16; y++) {
                for (int z = -64; z <= 64; z++) {

                    BlockPos pos = center.offset(x, y, z);

                    var block = level.getBlockState(pos).getBlock();

                    if (
                            block == Blocks.TUBE_CORAL_BLOCK
                                    || block == Blocks.BRAIN_CORAL_BLOCK
                                    || block == Blocks.BUBBLE_CORAL_BLOCK
                                    || block == Blocks.FIRE_CORAL_BLOCK
                                    || block == Blocks.HORN_CORAL_BLOCK
                                    || block == Blocks.TUBE_CORAL
                                    || block == Blocks.BRAIN_CORAL
                                    || block == Blocks.BUBBLE_CORAL
                                    || block == Blocks.FIRE_CORAL
                                    || block == Blocks.HORN_CORAL
                                    || block == Blocks.TUBE_CORAL_FAN
                                    || block == Blocks.BRAIN_CORAL_FAN
                                    || block == Blocks.BUBBLE_CORAL_FAN
                                    || block == Blocks.FIRE_CORAL_FAN
                                    || block == Blocks.HORN_CORAL_FAN
                    ) {
                        return pos;
                    }
                }
            }
        }

        return null;
    }
}