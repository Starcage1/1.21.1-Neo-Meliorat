package net.starcage.meliorat.common.sniffer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.config.MelioratConfig;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class SnifferLootHandler {

    private static final ResourceLocation SNIFFER_DIGGING =
            ResourceLocation.withDefaultNamespace(
                    "gameplay/sniffer_digging"
            );

    @SubscribeEvent
    public static void onLootTableLoad(
            LootTableLoadEvent event
    ) {

        if (!MelioratConfig.ENABLE_SNIFFER_ARCHAEOLOGY.get())
            return;

        if (!event.getName().equals(SNIFFER_DIGGING))
            return;

        LootPool archaeologyPool =
                LootPool.lootPool()
                        .name("meliorat_sniffer")

                        .setRolls(
                                ConstantValue.exactly(1)
                        )

                        .when(
                                LootItemRandomChanceCondition.randomChance(
                                         MelioratConfig.SNIFFER_ARCHAEOLOGY_CHANCE
                                                .get()
                                                .floatValue()
                                )
                        )

                        // Seeds (Common)
                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(10))
                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10))
                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10))
                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10))
                        .add(LootItem.lootTableItem(Items.TORCHFLOWER_SEEDS).setWeight(10))
                        .add(LootItem.lootTableItem(Items.PITCHER_POD).setWeight(10))

                        // Pottery Sherds (Rare)
                        .add(LootItem.lootTableItem(Items.ANGLER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.ARCHER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.BREWER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.BURN_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.DANGER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.EXPLORER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.FLOW_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.FRIEND_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.GUSTER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.HEART_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.HEARTBREAK_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.HOWL_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.MINER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.MOURNER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.PLENTY_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.PRIZE_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.SCRAPE_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.SHEAF_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.SHELTER_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(2))
                        .add(LootItem.lootTableItem(Items.SNORT_POTTERY_SHERD).setWeight(2))

                        // Disc Fragment (Very Rare)
                        .add(
                                LootItem.lootTableItem(
                                        Items.DISC_FRAGMENT_5
                                ).setWeight(1)
                        )

                        // Archaeology Templates (Very Rare)
                        .add(
                                LootItem.lootTableItem(
                                        Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE
                                ).setWeight(1)
                        )
                        .add(
                                LootItem.lootTableItem(
                                        Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE
                                ).setWeight(1)
                        )
                        .add(
                                LootItem.lootTableItem(
                                        Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE
                                ).setWeight(1)
                        )
                        .add(
                                LootItem.lootTableItem(
                                        Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE
                                ).setWeight(1)
                        )

                        .build();

        event.getTable().addPool(
                archaeologyPool
        );
    }
}