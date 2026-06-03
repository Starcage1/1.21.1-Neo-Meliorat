package net.starcage.meliorat.common.cauldron.block;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class HoneyCauldronBlock extends LayeredCauldronBlock {

    public HoneyCauldronBlock() {
        super(
                Biome.Precipitation.NONE,
                CauldronInteraction.WATER,
                BlockBehaviour.Properties.ofLegacyCopy(
                        Blocks.CAULDRON
                )
        );
    }
}
