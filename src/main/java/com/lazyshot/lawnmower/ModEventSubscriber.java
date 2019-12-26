package com.lazyshot.lawnmower;

import com.lazyshot.lawnmower.config.LawnMowerConfig;
import com.lazyshot.lawnmower.init.ModItemGroups;
import com.lazyshot.lawnmower.items.Mower;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(
        modid = LawnMower.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ModEventSubscriber {
    private static Logger LOGGER = LogManager.getLogger(ModEventSubscriber.class);

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                setup(new Mower(1, 3), "manual_mower"),
                setup(new Mower(2, 5), "powered_mower"),
                setup(new Mower(5, 11), "riding_mower")
        );
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
        final ModConfig config = event.getConfig();

        if (config.getSpec() == LawnMowerConfig.SPEC) {
            LOGGER.debug("Baked client config: {}", config.getConfigData().toString());
            LOGGER.debug("Set Config: {}", LawnMowerConfig.mowedBlocks);
        }
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(LawnMower.MODID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }

}
