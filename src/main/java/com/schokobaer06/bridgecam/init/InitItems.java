package com.schokobaer06.bridgecam.init;

import com.schokobaer06.bridgecam.BridgeCam;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BridgeCam.MOD_ID);

    public static final RegistryObject<Item> MONITOR_BLOCK_ITEM = ITEMS.register("monitor",
            () -> new BlockItem(InitBlocks.MONITOR_BLOCK.get(), new Item.Properties()
            ));

    public static final RegistryObject<Item> WRENCH_ITEM = ITEMS.register("wrench",
            () -> new com.schokobaer06.bridgecam.item.WrenchItem(new Item.Properties()
                    .stacksTo(1)
            ));
    public static final RegistryObject<Item> SIMPLE_CAMERA_BLOCK_ITEM = ITEMS.register("simple_camera",
            () -> new BlockItem(InitBlocks.SIMPLE_CAMERA_BLOCK.get(), new Item.Properties()
            ));
}
