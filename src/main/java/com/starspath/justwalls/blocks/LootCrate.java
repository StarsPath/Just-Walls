package com.starspath.justwalls.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.ArrayList;

public class LootCrate extends Block {

    public static BooleanProperty MASTER = BooleanProperty.create("master");
    public static IntegerProperty ID = IntegerProperty.create("id", 0, 7);

    public LootCrate(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.FACING);
        builder.add(MASTER);
        builder.add(ID);
    }

    @Override
    public void onRemove(BlockState prevBlockState, Level level, BlockPos blockPos, BlockState nextBlockPos, boolean flag) {
        if(level.isClientSide()){
            return;
        }

        Direction direction = prevBlockState.getValue(BlockStateProperties.FACING);
        Boolean isMaster = prevBlockState.getValue(MASTER);

        if(isMaster){
            masterBreak(level, blockPos, prevBlockState);
            return;
        }

        BlockPos masterPos;
        int id = prevBlockState.getValue(ID);

        masterPos = switch (id) {
            default -> blockPos;
            case 1 -> blockPos.relative(direction.getOpposite());
            case 2 -> blockPos.relative(direction.getCounterClockWise());
            case 3 -> blockPos.relative(direction.getCounterClockWise()).relative(direction.getOpposite());
            case 4 -> blockPos.below();
            case 5 -> blockPos.relative(direction.getOpposite()).below();
            case 6 -> blockPos.relative(direction.getCounterClockWise()).below();
            case 7 -> blockPos.relative(direction.getCounterClockWise()).relative(direction.getOpposite()).below();
        };

        if(isMaster(level.getBlockState(masterPos), prevBlockState)){
            LootCrate masterCrate = (LootCrate)level.getBlockState(masterPos).getBlock();
            masterCrate.masterBreak(level, masterPos, level.getBlockState(masterPos));
        }

        super.onRemove(prevBlockState, level, blockPos, nextBlockPos, flag);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        if(levelAccessor.isClientSide()){
            return;
        }

        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        Boolean isMaster = blockState.getValue(MASTER);

        if(isMaster){
            masterBreak(levelAccessor, blockPos, blockState);
            return;
        }

        BlockPos masterPos;
        int id = blockState.getValue(ID);

        masterPos = switch (id) {
            default -> blockPos;
            case 1 -> blockPos.relative(direction.getOpposite());
            case 2 -> blockPos.relative(direction.getCounterClockWise());
            case 3 -> blockPos.relative(direction.getCounterClockWise()).relative(direction.getOpposite());
            case 4 -> blockPos.below();
            case 5 -> blockPos.relative(direction.getOpposite()).below();
            case 6 -> blockPos.relative(direction.getCounterClockWise()).below();
            case 7 -> blockPos.relative(direction.getCounterClockWise()).relative(direction.getOpposite()).below();
        };

        if(isMaster(levelAccessor.getBlockState(masterPos), blockState)){
            LootCrate masterCrate = (LootCrate)levelAccessor.getBlockState(masterPos).getBlock();
            masterCrate.masterBreak(levelAccessor, masterPos, levelAccessor.getBlockState(masterPos));
        }

        super.destroy(levelAccessor, blockPos, blockState);
    }

    protected Boolean isMaster(BlockState blockState, BlockState self){
        return blockState.getBlock() == self.getBlock() &&
                blockState.getValue(BlockStateProperties.FACING) == self.getValue(BlockStateProperties.FACING) &&
                blockState.getValue(MASTER) &&
                blockState.getValue(ID) == 0
                ;
    }

    protected void masterBreak(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState){
        Direction direction = blockState.getValue(BlockStateProperties.FACING);

        BlockPos masterPos = blockPos;
        BlockPos pos1 = masterPos.relative(direction);
        BlockPos pos2 = masterPos.relative(direction.getClockWise());
        BlockPos pos3 = masterPos.relative(direction.getClockWise()).relative(direction);
        BlockPos pos4 = masterPos.above();
        BlockPos pos5 = masterPos.relative(direction).above();
        BlockPos pos6 = masterPos.relative(direction.getClockWise()).above();
        BlockPos pos7 = masterPos.relative(direction.getClockWise()).relative(direction).above();

        ArrayList<BlockPos> blockPosList =  new ArrayList<>();
        blockPosList.add(masterPos);
        blockPosList.add(pos1);
        blockPosList.add(pos2);
        blockPosList.add(pos3);
        blockPosList.add(pos4);
        blockPosList.add(pos5);
        blockPosList.add(pos6);
        blockPosList.add(pos7);

        for(BlockPos pos: blockPosList){
            levelAccessor.destroyBlock(pos, true);
        }
    }
}
