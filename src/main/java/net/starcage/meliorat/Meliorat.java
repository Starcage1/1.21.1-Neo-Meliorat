package net.starcage.meliorat;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.starcage.meliorat.common.command.ClearChatCommand;
import net.starcage.meliorat.network.ModPayloads;
import net.starcage.meliorat.registry.*;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.starcage.meliorat.common.fletchingtable.FletchingScreen;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.config.ModConfig;
import net.starcage.meliorat.config.MelioratConfig;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Meliorat.MOD_ID)
public class Meliorat {
    public static final String MOD_ID = "meliorat";
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Meliorat(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModPayloads::register);

        // Register ourselves for server and other game events we are interested in.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenus.register(modEventBus);
        ModRecipeTypes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us

        modContainer.registerConfig(
                ModConfig.Type.COMMON,
                MelioratConfig.SPEC
        );
    }

    @SubscribeEvent
    public void registerCommands(
            RegisterCommandsEvent event
    ) {

        ClearChatCommand.register(
                event.getDispatcher()
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(
                    ModMenus.FLETCHING_MENU.get(),
                    FletchingScreen::new
            );
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
