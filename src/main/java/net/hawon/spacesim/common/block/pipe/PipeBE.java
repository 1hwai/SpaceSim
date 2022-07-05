package net.hawon.spacesim.common.block.pipe;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PipeBE extends BlockEntity {

    public PipeBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public final CompoundTag getUpdateTag() {
        return writeUpdate(super.getUpdateTag());
    }

    public CompoundTag writeUpdate(CompoundTag tag) {
        return tag;
    }

}
