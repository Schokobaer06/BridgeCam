package com.schokobaer06.bridgecam.init;

import com.schokobaer06.bridgecam.BridgeCam;
import com.schokobaer06.bridgecam.block.MonitorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BridgeCam.MOD_ID);

    public static final RegistryObject<Block> MONITOR_BLOCK = BLOCKS.register("monitor",
            () -> new MonitorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .sound(SoundType.METAL)
                    .strength(1.5f, 6.0f)
            ));
    public static final RegistryObject<Block> SIMPLE_CAMERA_BLOCK = BLOCKS.register("simple_camera",
            () -> new MonitorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(0.5f, 1.0f)
                    .noOcclusion()
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 3) // Leichtes Glühen für die Kamera
            ));
}