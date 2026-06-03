package net.starcage.meliorat.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.common.fletchingtable.FletchingMenu;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(
                    BuiltInRegistries.MENU,
                    Meliorat.MOD_ID
            );

    public static final net.neoforged.neoforge.registries.DeferredHolder<
            MenuType<?>,
            MenuType<FletchingMenu>
            > FLETCHING_MENU =
            MENUS.register(
                    "fletching_menu",
                    () -> new MenuType<>(
                            FletchingMenu::new,
                            net.minecraft.world.flag.FeatureFlags.DEFAULT_FLAGS
                    )
            );

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
