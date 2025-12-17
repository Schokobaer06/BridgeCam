package com.schokobaer06.bridgecam.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class SimpleCameraBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final Map<Direction, VoxelShape> SHAPES = new HashMap<>();

    static {
        // Basis-Shape für NORTH (deine ursprüngliche Form)
        VoxelShape baseShape = makeBaseShape();

        // Für jede Richtung die passende Rotation berechnen
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            SHAPES.put(direction, rotateShape(baseShape, direction));
        }
    }

    public SimpleCameraBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private static VoxelShape makeBaseShape() {
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

    private static VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        // Deine Shape ist für FACING=NORTH definiert
        // Wir rotieren sie basierend auf der gewünschten Richtung
        return switch (direction) {
            case NORTH -> shape; // Original
            case SOUTH -> rotateShape(shape, 180);
            case WEST -> rotateShape(shape, 90);
            case EAST -> rotateShape(shape, 270);
            default -> shape;
        };
    }

    private static VoxelShape rotateShape(VoxelShape shape, int degrees) {
        // Einfache 2D-Rotation um die Y-Achse für die Hitbox
        VoxelShape[] result = new VoxelShape[]{Shapes.empty()};

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Rotiere die Box-Koordinaten
            double[] rotatedMin = rotatePoint(minX - 0.5, minZ - 0.5, degrees);
            double[] rotatedMax = rotatePoint(maxX - 0.5, maxZ - 0.5, degrees);

            // Zurück zu 0-1 Koordinaten
            double newMinX = Math.min(rotatedMin[0], rotatedMax[0]) + 0.5;
            double newMinZ = Math.min(rotatedMin[1], rotatedMax[1]) + 0.5;
            double newMaxX = Math.max(rotatedMin[0], rotatedMax[0]) + 0.5;
            double newMaxZ = Math.max(rotatedMin[1], rotatedMax[1]) + 0.5;

            result[0] = Shapes.or(result[0], Shapes.box(
                    newMinX, minY, newMinZ,
                    newMaxX, maxY, newMaxZ
            ));
        });

        return result[0];
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    private static double[] rotatePoint(double x, double z, double degrees) {
        double radians = Math.toRadians(degrees);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        return new double[]{
                x * cos - z * sin,  // new X
                x * sin + z * cos   // new Z
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }
}