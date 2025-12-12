package com.schokobaer06.bridgecam.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class SimpleCameraBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 3);

    public SimpleCameraBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ROTATION, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ROTATION);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getClickedFace();

        Direction horizontalDirection = context.getHorizontalDirection();
        int rotation = getRotationFromDirection(horizontalDirection, facing);

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(ROTATION, rotation);
    }

    private int getRotationFromDirection(Direction playerHorizontalDir, Direction blockFacing) {
        // Wenn der Block an Wand, Decke oder Boden hängt, brauchen wir unterschiedliche Rotation-Logik
        if (blockFacing == Direction.UP || blockFacing == Direction.DOWN) {
            // Block an Decke oder Boden: Rotation basierend auf Spielerrichtung
            return switch (playerHorizontalDir) {
                case NORTH -> 0;
                case EAST -> 1;
                case SOUTH -> 2;
                case WEST -> 3;
                default -> 0;
            };
        } else {
            // Block an Wand: Rotation relativ zur Wand
            // Hier könnte komplexere Logik stehen, je nach gewünschtem Verhalten
            return 0; // Einfachheitshalber erstmal 0
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        // Rotation mit Rechtsklick (ohne Schraubenzieher)
        return state.setValue(ROTATION, (state.getValue(ROTATION) + 1) % 4);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        // Für Spiegelung (optional)
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    // Hilfsmethode für später: Richtungsvektor der Kamera berechnen
    public static Direction getCameraDirection(BlockState state) {
        Direction facing = state.getValue(FACING);
        int rotation = state.getValue(ROTATION);

        // Hier berechnen wir die tatsächliche Blickrichtung der Kamera
        // Das hängt davon ab, wie die Kamera angebracht ist (FACING)
        // und wie sie rotiert ist (ROTATION)
        // Beispiel-Logik (anpassen je nach Modell-Ausrichtung):
        if (facing == Direction.UP) {
            // An Decke: schaut nach unten + Rotation
            return switch (rotation) {
                case 0 -> Direction.NORTH;
                case 1 -> Direction.EAST;
                case 2 -> Direction.SOUTH;
                case 3 -> Direction.WEST;
                default -> Direction.NORTH;
            };
        } else if (facing == Direction.DOWN) {
            // Am Boden: schaut nach oben + Rotation
            return switch (rotation) {
                case 0 -> Direction.SOUTH;
                case 1 -> Direction.WEST;
                case 2 -> Direction.NORTH;
                case 3 -> Direction.EAST;
                default -> Direction.SOUTH;
            };
        } else {
            // An Wand: schaut parallel zur Wand
            return facing;
        }
    }
}
