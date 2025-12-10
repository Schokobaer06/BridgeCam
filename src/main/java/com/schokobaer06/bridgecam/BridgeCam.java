package com.schokobaer06.bridgecam;

import com.schokobaer06.bridgecam.init.InitBlocks;
import com.schokobaer06.bridgecam.init.InitTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BridgeCam.MOD_ID)
public class BridgeCam {
    public static final String MOD_ID = "bridgecam";

    public BridgeCam() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        InitBlocks.register(bus);
        InitTabs.CREATIVE_MODE_TABS.register(bus);
    }
}
