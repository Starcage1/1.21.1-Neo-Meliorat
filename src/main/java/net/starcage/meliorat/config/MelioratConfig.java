package net.starcage.meliorat.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class MelioratConfig {

    public static final ModConfigSpec.Builder BUILDER =
            new ModConfigSpec.Builder();

    public static final ModConfigSpec SPEC;

    // Module Toggles

    public static final ModConfigSpec.BooleanValue ENABLE_CLOCK;
    public static final ModConfigSpec.BooleanValue ENABLE_TIPPED_ARROWS;
    public static final ModConfigSpec.BooleanValue ENABLE_CAULDRON;
    public static final ModConfigSpec.BooleanValue ENABLE_OCELOT_GUARDIAN;

    // Clock

    public static final ModConfigSpec.BooleanValue PSEUDO_VANILLA_CLOCK;

    // Tipped Arrows

    public static final ModConfigSpec.DoubleValue ARROW_RADIUS;

    public static final ModConfigSpec.IntValue ARROW_DURATION;

    // Cauldron

    public static final ModConfigSpec.IntValue HONEY_REQUIRED;

    public static final ModConfigSpec.IntValue SLIME_REQUIRED;

    static {

        BUILDER.push("modules");

        ENABLE_CLOCK =
                BUILDER.define(
                        "enableClock",
                        true
                );

        ENABLE_TIPPED_ARROWS =
                BUILDER.define(
                        "enableTippedArrows",
                        true
                );

        ENABLE_CAULDRON =
                BUILDER.define(
                        "enableCauldron",
                        true
                );

        ENABLE_OCELOT_GUARDIAN =
                BUILDER.define(
                        "enableOcelotGuardian",
                        true
                );

        BUILDER.pop();

        BUILDER.push("clock");

        PSEUDO_VANILLA_CLOCK =
                BUILDER.define(
                        "pseudoVanillaClock",
                        true
                );

        BUILDER.pop();

        BUILDER.push("tipped_arrows");

        ARROW_RADIUS =
                BUILDER.defineInRange(
                        "radius",
                        3.0D,
                        1.0D,
                        3.0D
                );

        ARROW_DURATION =
                BUILDER.defineInRange(
                        "durationSeconds",
                        5,
                        5,
                        30
                );

        BUILDER.pop();

        BUILDER.push("cauldron");

        HONEY_REQUIRED =
                BUILDER.defineInRange(
                        "honeyBottlesRequired",
                        3,
                        2,
                        3
                );

        SLIME_REQUIRED =
                BUILDER.defineInRange(
                        "slimeballsRequired",
                        3,
                        2,
                        3
                );

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
