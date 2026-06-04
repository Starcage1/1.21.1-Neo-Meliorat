package net.starcage.meliorat.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.starcage.meliorat.config.MelioratConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AbstractMinecart.class)
public class MinecartSpeedMixin {

    @Inject(
            method = "getMaxSpeedWithRail",
            at = @At("RETURN"),
            cancellable = true
    )

    private void meliorat$maxRailSpeed(

            CallbackInfoReturnable<Double> cir
    ) {
        if (!MelioratConfig.ENABLE_MINECART_SPEEDBOOST.get())
            return;

        AbstractMinecart cart =
                (AbstractMinecart)(Object)this;

        if (!(cart instanceof MinecartFurnace))
            return;

        cir.setReturnValue(
                MelioratConfig.FURNACE_MINECART_SPEED
                        .get() / 20.0D
        );
    }

    @Inject(
            method = "moveAlongTrack",
            at = @At("TAIL")
    )
    private void meliorat$boost(
            BlockPos pos,
            BlockState state,
            CallbackInfo ci
    ) {

        if (!MelioratConfig.ENABLE_MINECART_SPEEDBOOST.get())
            return;

        AbstractMinecart cart =
                (AbstractMinecart)(Object)this;

        if (cart instanceof MinecartFurnace)
            return;

        Vec3 motion = cart.getDeltaMovement();

        double currentSpeed =
                motion.horizontalDistance();

        double targetSpeed =
                MelioratConfig.MINECART_SPEED.get()
                        / 20.0D;

        if (currentSpeed > 0.001 &&
                currentSpeed < targetSpeed) {

            double scale =
                    Math.min(
                            targetSpeed / currentSpeed,
                            1.20
                    );

            cart.setDeltaMovement(
                    motion.x * scale,
                    motion.y,
                    motion.z * scale
            );
        }
    }
}