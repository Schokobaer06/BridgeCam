package com.schokobaer06.bridgecam.init;

import com.schokobaer06.bridgecam.BridgeCam;
import com.schokobaer06.bridgecam.block.MonitorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BridgeCam.MOD_ID);
    // Monitor Block registrieren
    public static final RegistryObject<Block> MONITOR_BLOCK = BLOCKS.register("monitor",
            () -> new MonitorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(1.5f, 6.0f)
            ));
}