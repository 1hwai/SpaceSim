package net.hawon.spacesim.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CopperCableBlockEntity extends BlockEntity {

    public CopperCableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tickServer() {
        BlockState blockState = level.getBlockState(worldPosition);
        if (isPowered(worldPosition)) {
            level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWER, 10), Block.UPDATE_ALL);
        }
    }

    private boolean isPowered(BlockPos pos) {
        if (level.getBlockState(pos.above()).hasProperty(BlockStateProperties.POWER)) {
            return true;
        }
        if (level.getBlockState(pos.below()).hasProperty(BlockStateProperties.POWERED)) {
            return true;
        }
        //level.getBlockState(pos.above()).getValue(BlockStateProperties.POWER);
        return false;
    }

//    private boolean isOvercurrented() {
//
//    }

}
