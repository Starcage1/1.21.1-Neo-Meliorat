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
    public static final ModConfigSpec.BooleanValue ENABLE_MINECART_SPEEDBOOST;
    public static final ModConfigSpec.BooleanValue ENABLE_WOODEN_TOOLS_IMPROVEMENTS;
    public static final ModConfigSpec.BooleanValue ENABLE_ITEM_RENAMING;
    public static final ModConfigSpec.BooleanValue ENABLE_BEETROOT_SOUP;

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

    // Minecarts

    public static final ModConfigSpec.IntValue MINECART_SPEED;
    public static final ModConfigSpec.IntValue FURNACE_MINECART_SPEED;

    // Wooden Tools

    public static final ModConfigSpec.DoubleValue WOOD_AXE_LOG_CHANCE;
    public static final ModConfigSpec.DoubleValue WOOD_HOE_COMPOST_CHANCE;
    public static final ModConfigSpec.DoubleValue WOOD_SHOVEL_FLINT_BONUS;
    public static final ModConfigSpec.IntValue WOODEN_SHOVEL_CAMPFIRE_BOOST;
    public static final ModConfigSpec.IntValue WOODEN_HOE_CAMPFIRE_BOOST;

    static {

        BUILDER.comment("Enable or disable individual Meliorat modules.")
                .push("modules");

        ENABLE_CLOCK = BUILDER
                .comment("Enables placeable clocks.")
                .define("enableClock", true);

        ENABLE_TIPPED_ARROWS = BUILDER
                .comment("Enables tipped arrow area-of-effect cloud behavior when arrows land.")
                .define("enableTippedArrows", true);

        ENABLE_CAULDRON = BUILDER
                .comment("Enables cauldron crafting improvements, such as creating honey and slime blocks.")
                .define("enableCauldron", true);

        ENABLE_OCELOT_GUARDIAN = BUILDER
                .comment("Enables ocelots scaring away specific hostile mobs.")
                .define("enableOcelotGuardian", true);

        ENABLE_FLETCHING_TABLE = BUILDER
                .comment("Enables fletching table functionality for crafting archery related items.")
                .define("enableFletchingTable", true);

        ENABLE_SNIFFER_ARCHAEOLOGY = BUILDER
                .comment("Enables sniffers having a chance to unearth archaeology related items.")
                .define("enableSnifferArchaeology", true);

        ENABLE_DOLPHIN_GUIDE = BUILDER
                .comment("Enables dolphins guiding players to nearby coral reefs before other structures.")
                .define("enableDolphinGuide", true);

        ENABLE_MINECART_SPEEDBOOST = BUILDER
                .comment("Enables the configurable minecart speed boost. See the [minecart] section for speed values.")
                .define("enableMinecartSpeedboost", true);

        ENABLE_WOODEN_TOOLS_IMPROVEMENTS = BUILDER
                .comment("Enables wooden tool improvements, such as bonus log drops, compost chances, and campfire cooking progress boosts.")
                .define("enableWoodenToolsImprovements", true);

        ENABLE_ITEM_RENAMING = BUILDER
                .comment("Enables item renaming using an inc sac/glow inc sac using right click. Works for empty maps, filled maps and nametags.")
                .define("enableNameTagRenaming", true);

        ENABLE_BEETROOT_SOUP = BUILDER
                .comment("Enables beetroot soup buff that provides extra staturation duration.")
                        .define("enableBeetrootSoup",true);

        BUILDER.pop();

        // ----------------------------------------------------------------

        BUILDER.comment("Settings for the clock module.").push("clock");

        PSEUDO_VANILLA_CLOCK = BUILDER
                .comment(
                        "When true, the clock displays time in a toned down vanilla-style format, with 4 states.",
                        "When false, placed clocks no longer update and remain static."
                )
                .define("pseudoVanillaClock", true);

        BUILDER.pop();

        // ----------------------------------------------------------------

        BUILDER.comment("Settings for the tipped arrows module.").push("tipped_arrows");

        ARROW_RADIUS = BUILDER
                .comment(
                        "The radius (in blocks) of the lingering cloud created when a tipped arrow lands.",
                        "Range: 1.0 – 3.0"
                )
                .defineInRange("radius", 3.0D, 1.0D, 3.0D);

        ARROW_DURATION = BUILDER
                .comment(
                        "How long (in seconds) the lingering cloud created by a tipped arrow lasts.",
                        "Range: 5 – 30"
                )
                .defineInRange("durationSeconds", 5, 5, 30);

        BUILDER.pop();

        // ----------------------------------------------------------------

        BUILDER.comment("Settings for the cauldron module.").push("cauldron");

        HONEY_REQUIRED = BUILDER
                .comment(
                        "Number of honey bottles required for the cauldron honey block crafting recipe.",
                        "Range: 2 – 3"
                )
                .defineInRange("honeyBottlesRequired", 3, 2, 3);

        SLIME_REQUIRED = BUILDER
                .comment(
                        "Number of slimeballs required for the cauldron slime block crafting recipe.",
                        "Range: 2 – 3"
                )
                .defineInRange("slimeballsRequired", 3, 2, 3);

        BUILDER.pop();

        // ----------------------------------------------------------------

        BUILDER.comment("Settings for the sniffer archaeology module.").push("sniffer");

        SNIFFER_ARCHAEOLOGY_CHANCE = BUILDER
                .comment(
                        "The probability (0.0 – 1.0) that a sniffer uncovers an archaeology related item when digging.",
                        "0.0 = never, 1.0 = always. Default is 0.20 (20% chance)."
                )
                .defineInRange("snifferArchaeologyChance", 0.20, 0.0, 1.0);

        BUILDER.pop();

        // ----------------------------------------------------------------

        BUILDER.comment("Settings for the minecart speed module.").push("minecart");

        MINECART_SPEED = BUILDER
                .comment(
                        "Maximum speed of regular minecarts except furnace, in blocks per second.",
                        "Vanilla default is 8. Range: 8 – 64."
                )
                .defineInRange("minecartSpeed", 12, 8, 64);

        FURNACE_MINECART_SPEED = BUILDER
                .comment(
                        "Maximum speed of furnace minecarts, in blocks per second.",
                        "Vanilla default is 8. Range: 8 – 64."
                )
                .defineInRange("furnaceMinecartSpeed", 12, 8, 64);

        BUILDER.pop();

        // ----------------------------------------------------------------

        BUILDER.comment("Settings for the wooden tools improvements module.").push("wooden_tools");

        WOOD_AXE_LOG_CHANCE = BUILDER
                .comment(
                        "Probability (0.0 – 1.0) that a wooden axe drops an extra log when chopping.",
                        "0.0 = never, 1.0 = always. Default is 0.25 (25% chance)."
                )
                .defineInRange("woodAxeExtraLogChance", 0.25D, 0.0D, 1.0D);

        WOOD_HOE_COMPOST_CHANCE = BUILDER
                .comment(
                        "Chance for an offhand wooden hoe to increase compost level by an additional stage. Consumes 1 durability when triggered.",
                        "0.0 = never, 1.0 = always. Default is 0.15 (15% chance)."
                )
                .defineInRange("woodHoeCompostChance", 0.15D, 0.0D, 1.0D);

        WOOD_SHOVEL_FLINT_BONUS = BUILDER
                .comment(
                        "Additional chance added to the normal flint drop chance.",
                        "0.0 = no bonus. Default is 0.10 (+10%)."
                )
                .defineInRange("woodShovelFlintBonus", 0.10D, 0.0D, 1.0D);

        WOODEN_SHOVEL_CAMPFIRE_BOOST = BUILDER
                .comment(
                        "Campfire cooking progress added by a wooden shovel.",
                        "20 ticks = 1 second. Range: 0 – 600 ticks (0 – 30 seconds)."
                )
                .defineInRange("woodenShovelCampfireBoostTicks", 60, 0, 600);

        WOODEN_HOE_CAMPFIRE_BOOST = BUILDER
                .comment(
                        "Campfire cooking progress added by a wooden hoe.",
                        "20 ticks = 1 second. Range: 0 – 600 ticks (0 – 30 seconds)."
                )
                .defineInRange("woodenHoeCampfireBoostTicks", 100, 0, 600);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
