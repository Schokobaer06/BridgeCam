package com.schokobaer06.bridgecam;

import com.schokobaer06.bridgecam.init.InitBlocks;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

@Mod(BridgeCam.MOD_ID)
public class BridgeCam {
    public static final String MOD_ID = "bridgecam";

    public BridgeCam() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        InitBlocks.register(bus);

        bus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        for (RegistryObject<Item> item: InitBlocks.ITEMS.getEntries()) {
            if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
                event.accept(item.get());
            }
        }
    }
}
