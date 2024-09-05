package com.starspath.justwalls.blocks;

import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.init.ModBlocks;
import com.starspath.justwalls.utils.DoorVoxelShapes;
import com.starspath.justwalls.utils.Tiers;
import com.starspath.justwalls.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WallDoor extends StructureBlock {

    public static BooleanProperty OPEN = BooleanProperty.create("open");
    public static IntegerProperty INDEX = IntegerProperty.create("index", 0, 8);

    public WallDoor(Tiers.TIER tier){
        super(tier);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier).setValue(INDEX, 0));
    }

    public WallDoor(Properties properties, Tiers.TIER tier) {
        super(properties, tier);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier).setValue(INDEX, 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        int index = blockState.getValue(INDEX);
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        boolean open = blockState.getValue(OPEN);
        VoxelShape result = Shapes.empty();
        if(!open){
            result = switch (index){
                case 0, 1 -> DoorVoxelShapes.doorTrim();
                case 6, 7 -> DoorVoxelShapes.rotateShape180Degrees(DoorVoxelShapes.doorTrim());
                case 2 -> DoorVoxelShapes.doorCorner();
                case 8 -> DoorVoxelShapes.rotateShape180Degrees(DoorVoxelShapes.doorCorner());
                case 3, 4 -> DoorVoxelShapes.doorCenter();
                case 5 -> DoorVoxelShapes.doorTop();
                default -> super.getShape(blockState, blockGetter, blockPos, collisionContext);
            };
        }
        else{
            result = switch (index){
                case 0, 1 -> DoorVoxelShapes.doorTrimOpen();
                case 6, 7 -> DoorVoxelShapes.rotateShape180Degrees(DoorVoxelShapes.doorTrimOpen());
                case 2 -> DoorVoxelShapes.doorCornerOpen();
                case 8 -> DoorVoxelShapes.rotateShape180Degrees(DoorVoxelShapes.doorCornerOpen());
                case 3, 4 -> Shapes.empty();
                case 5 -> DoorVoxelShapes.doorTopOpen();
                default -> super.getShape(blockState, blockGetter, blockPos, collisionContext);
            };
        }

        if(direction == Direction.SOUTH){
            return DoorVoxelShapes.rotateShape180Degrees(result);
        }
        if(direction == Direction.WEST){
            return DoorVoxelShapes.rotateShape90DegreesCounterClockwise(result);
        }
        if(direction == Direction.EAST){
            return DoorVoxelShapes.rotateShape90DegreesClockwise(result);
        }
        return result;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN, INDEX);
    }

    @Override
    public float getShadeBrightness(BlockState p_220080_1_, BlockGetter p_220080_2_, BlockPos p_220080_3_) {
        return 1.0f;
    }

    @Override
    protected Boolean isMaster(BlockState blockState, BlockState self){
        return blockState.getBlock() == self.getBlock() && blockState.getValue(BlockStateProperties.FACING) == self.getValue(BlockStateProperties.FACING) && blockState.getValue(MASTER);
    }

    @Override
    public BlockPos getMasterPos(LevelAccessor level, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                if(isMaster(level.getBlockState(checkPos), blockState)){
                    return checkPos;
                }
            }
        }
        return null;
    }

    @Override
    protected ArrayList<BlockPos> getChildPos(LevelAccessor level, BlockPos blockPos, BlockState blockState){
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        ArrayList<BlockPos> childPos = new ArrayList<>();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                BlockState checkBlockState = level.getBlockState(checkPos);
                if(checkBlockState.getBlock() == this && checkBlockState.getValue(BlockStateProperties.FACING) == direction){
                    childPos.add(checkPos);
                }
            }
        }
        return childPos;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if(level.isClientSide()){
            return InteractionResult.PASS;
        }

        Boolean isMaster = blockState.getValue(MASTER);

        if(interactionHand == InteractionHand.MAIN_HAND){
            if(isMaster){
                return masterToggleDoor(level, blockPos, blockState);
            }

            BlockPos masterPos = getMasterPos(level, blockPos, blockState);
            if(level.getBlockState(masterPos).getBlock() instanceof WallDoor masterDoor){
                return masterDoor.masterToggleDoor(level, masterPos, level.getBlockState(masterPos));
            }
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    protected InteractionResult masterToggleDoor(Level level, BlockPos blockPos, BlockState blockState){
        boolean open = blockState.getValue(OPEN);
        ArrayList<BlockPos> childPosList = getChildPos(level, blockPos, blockState);
        ArrayList<BlockState> childBlockStateList = childPosList.stream().map(blockPos1 -> level.getBlockState(blockPos1)).collect(Collectors.toCollection(ArrayList::new));

        for(int i = 0; i < childPosList.size(); i++){
            BlockPos childPos = childPosList.get(i);
            BlockState childState = childBlockStateList.get(i);
            BlockState newState = childState.setValue(OPEN, !open);

            level.setBlock(childPos, newState, 10);
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    protected Block getNextTierBlock(Tiers.TIER tier){
        switch (tier){
            case THATCH -> {
                return ModBlocks.THATCH_WALL_DOOR.get();
            }
            case WOOD -> {
                return ModBlocks.WOODEN_WALL_DOOR.get();
            }
            case STONE -> {
                return ModBlocks.STONE_WALL_DOOR.get();
            }
            case METAL -> {
                return ModBlocks.METAL_WALL_DOOR.get();
            }
            case ARMOR -> {
                return ModBlocks.ARMORED_WALL_DOOR.get();
            }
        }
        return ModBlocks.THATCH_WALL_DOOR.get();
    }

    @Override
    public ItemStack getRequiredItemForUpgrade(BlockState blockState){
        return getRequiredItemForUpgrade(blockState, 9);
    }
}

