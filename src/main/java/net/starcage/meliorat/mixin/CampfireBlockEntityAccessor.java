package net.starcage.meliorat.mixin;

import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CampfireBlockEntity.class)
public interface CampfireBlockEntityAccessor {

    @Accessor("cookingProgress")
    int[] meliorat$getCookingProgress();

    @Accessor("cookingTime")
    int[] meliorat$getCookingTime();
}
