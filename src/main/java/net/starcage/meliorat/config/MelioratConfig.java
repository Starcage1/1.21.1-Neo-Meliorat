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
    public static final ModConfigSpec.BooleanValue ENABLE_FLETCHING_TABLE;
    public static final ModConfigSpec.BooleanValue ENABLE_SNIFFER_ARCHAEOLOGY;
    public static final ModConfigSpec.BooleanValue ENABLE_DOLPHIN_GUIDE;

    // Clock

    public static final ModConfigSpec.BooleanValue PSEUDO_VANILLA_CLOCK;

    // Tipped Arrows

    public static final ModConfigSpec.DoubleValue ARROW_RADIUS;

    public static final ModConfigSpec.IntValue ARROW_DURATION;

    // Cauldron

    public static final ModConfigSpec.IntValue HONEY_REQUIRED;

    public static final ModConfigSpec.IntValue SLIME_REQUIRED;

    // Sniffer

    public static final ModConfigSpec.DoubleValue SNIFFER_ARCHAEOLOGY_CHANCE;

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

        ENABLE_FLETCHING_TABLE =
                BUILDER.define(
                        "enableFletchingTable",
                        true
                );

        ENABLE_SNIFFER_ARCHAEOLOGY =
                BUILDER.define(
                        "enableSnifferArchaeology",
                        true
                );

        ENABLE_DOLPHIN_GUIDE =
                BUILDER.define(
                        "enableDolphinGuide",
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

        BUILDER.push("sniffer");

        SNIFFER_ARCHAEOLOGY_CHANCE =
                BUILDER.defineInRange(
                        "snifferArchaeologyChance",
                        0.20,
                        0.0,
                        1.0
                );

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
