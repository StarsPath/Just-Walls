package com.starspath.justwalls.utils;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.concurrent.atomic.AtomicReference;

public class DoorVoxelShapes {

    public static VoxelShape doorTrim(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.375, 1, 1, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.5, 1, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape doorCenter(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.375, 1, 1, 0.625), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape doorCorner(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.5, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.5, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.375, 1, 0.5, 0.625), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape doorTop(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.375, 1, 0.5, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.5, 0, 1, 1, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape doorTrimOpen(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.5, 1, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape doorCornerOpen(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.5, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.5, 0, 1, 1, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape doorTopOpen(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.5, 0, 1, 1, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape rotateShape90DegreesClockwise(VoxelShape shape) {
        AtomicReference<VoxelShape> rotatedShape = new AtomicReference<>(Shapes.empty());

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Calculate new positions by inverting both X and Z coordinates
            double newMinX = 1.0 - maxZ; // Swap and invert Z and X
            double newMinZ = minX;        // Swap X to Z
            double newMaxX = 1.0 - minZ;  // Same for the max points
            double newMaxZ = maxX;

            rotatedShape.set(Shapes.or(rotatedShape.get(), Shapes.box(newMinX, minY, newMinZ, newMaxX, maxY, newMaxZ)));
        });
        return rotatedShape.get();
    }

    public static VoxelShape rotateShape90DegreesCounterClockwise(VoxelShape shape) {
        AtomicReference<VoxelShape> rotatedShape = new AtomicReference<>(Shapes.empty());

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Calculate new positions by inverting both X and Z coordinates
            double newMinX = minZ;        // Swap Z to X
            double newMaxX = maxZ;        // Swap Z to X
            double newMinZ = 1.0 - maxX;  // Swap X to Z and invert
            double newMaxZ = 1.0 - minX;  // Swap X to Z and invert

            rotatedShape.set(Shapes.or(rotatedShape.get(), Shapes.box(newMinX, minY, newMinZ, newMaxX, maxY, newMaxZ)));
        });
        return rotatedShape.get();
    }

    public static VoxelShape rotateShape180Degrees(VoxelShape shape) {
        AtomicReference<VoxelShape> rotatedShape = new AtomicReference<>(Shapes.empty());

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Calculate new positions by inverting both X and Z coordinates
            double newMinX = 1.0 - maxX;
            double newMaxX = 1.0 - minX;
            double newMinZ = 1.0 - maxZ;
            double newMaxZ = 1.0 - minZ;

            rotatedShape.set(Shapes.or(rotatedShape.get(), Shapes.box(newMinX, minY, newMinZ, newMaxX, maxY, newMaxZ)));
        });
        return rotatedShape.get();
    }

    public static VoxelShape flipShapeHorizontallyByX(VoxelShape shape) {
        AtomicReference<VoxelShape> flippedShape = new AtomicReference<>(Shapes.empty());

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Invert the X coordinates to flip horizontally
            double newMinX = 1.0 - maxX;
            double newMaxX = 1.0 - minX;

            // Z and Y coordinates remain the same
            flippedShape.set(Shapes.or(flippedShape.get(), Shapes.box(newMinX, minY, minZ, newMaxX, maxY, maxZ)));
        });
        return flippedShape.get();
    }

    public static VoxelShape flipShapeHorizontallyByZ(VoxelShape shape) {
        AtomicReference<VoxelShape> flippedShape = new AtomicReference<>(Shapes.empty());

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Invert the X coordinates to flip horizontally
            double newMinZ = 1.0 - maxZ;
            double newMaxZ = 1.0 - minZ;

            // Z and Y coordinates remain the same
            flippedShape.set(Shapes.or(flippedShape.get(), Shapes.box(minX, minY, newMinZ, maxX, maxY, newMaxZ)));
        });
        return flippedShape.get();
    }
}
