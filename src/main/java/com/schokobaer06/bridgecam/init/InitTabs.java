package com.schokobaer06.bridgecam.init;

import com.schokobaer06.bridgecam.BridgeCam;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BridgeCam.MOD_ID);
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("creative_mode_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.bridgecam.tab"))
                    .icon(() -> new ItemStack(InitBlocks.MONITOR_BLOCK.get()))
                    .displayItems((parameters, tabData) -> {
                        InitItems.ITEMS.getEntries().forEach(
                                itemRegistryObject -> tabData.accept(itemRegistryObject.get())
                        );
                    })
                    .build());
}
