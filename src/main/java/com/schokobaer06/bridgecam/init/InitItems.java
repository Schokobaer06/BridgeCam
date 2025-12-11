package com.schokobaer06.bridgecam.init;

import com.schokobaer06.bridgecam.BridgeCam;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.schokobaer06.bridgecam.init.InitBlocks.MONITOR_BLOCK;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BridgeCam.MOD_ID);

    public static final RegistryObject<Item> MONITOR_BLOCK_ITEM = ITEMS.register("monitor",
            () -> new BlockItem(MONITOR_BLOCK.get(), new Item.Properties()
            ));
}
