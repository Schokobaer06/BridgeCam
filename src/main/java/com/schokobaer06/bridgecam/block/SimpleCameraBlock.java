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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
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
        if (blockFacing == Direction.UP) {
            return switch (playerHorizontalDir) {
                case NORTH -> 0;
                case EAST -> 1;
                case SOUTH -> 2;
                case WEST -> 3;
                default -> 0;
            };
        } else if (blockFacing == Direction.DOWN) {
            return switch (playerHorizontalDir) {
                case NORTH -> 2;
                case EAST -> 3;
                case SOUTH -> 0;
                case WEST -> 1;
                default -> 0;
            };
        } else {
            // Für Wände: Rotation basierend auf Spieler-Blickrichtung relativ zur Wand
            // Dies bestimmt, wohin die Kamera an der Wand schaut
            Direction oppositeWall = blockFacing.getOpposite();
            if (playerHorizontalDir == oppositeWall) return 0; // Von Wand weg
            if (playerHorizontalDir == oppositeWall.getClockWise()) return 1; // Rechts
            if (playerHorizontalDir == blockFacing) return 2; // In die Wand
            if (playerHorizontalDir == oppositeWall.getCounterClockWise()) return 3; // Links
            return 0;
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

    private VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.0625, 0.125, 0.6875, 0.4375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0, 0.3125, 0.5625, 0.0625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.8125, 0.5625, 0.0625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.3125, 0.5625, 0.0625, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.3125, 0.4375, 0.0625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.3125, 0.625, 0, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.1875, 0.11875, 0.5625, 0.3125, 0.11875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.3125, 0.09375, 0.59375, 0.40625, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.40625, 0.3125, 0.09375, 0.4375, 0.40625, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.3125, 0.09375, 0.65625, 0.34375, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.1875, 0.09375, 0.6875, 0.3125, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.1875, 0.09375, 0.4375, 0.3125, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.34375, 0.3125, 0.09375, 0.4375, 0.34375, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.34375, 0.15625, 0.09375, 0.4375, 0.1875, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.15625, 0.09375, 0.65625, 0.1875, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.0625, 0.09375, 0.5625, 0.1875, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.09375, 0.09375, 0.59375, 0.1875, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.40625, 0.09375, 0.09375, 0.4375, 0.1875, 0.09375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.3125, 0.09375, 0.5625, 0.4375, 0.09375), BooleanOp.OR);

        return shape;
    }
/*
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = makeShape();
        Direction facing = state.getValue(FACING);

        // Rotiere VoxelShape basierend auf FACING
        switch (facing) {
            case UP:
                return shape; // Normal
            case DOWN:
                return rotateShape(shape, Rotation.CLOCKWISE_180); // Um 180° um X
            case NORTH, WEST:
                return rotateShape(shape, Rotation.CLOCKWISE_90); // Um 90° um Z
            case SOUTH, EAST:
                return rotateShape(shape, Rotation.COUNTERCLOCKWISE_90); // Um -90° um Z
            default:
                return shape;
        }
    }

    private VoxelShape rotateShape(VoxelShape shape, Rotation rotation) {
        // Einfache Rotation um Y-Achse (für horizontale Ausrichtung an Wänden)
        // Für komplexere Rotationen müsstest du alle Shapes.box() einzeln transformieren
        return shape;
    }*/

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
