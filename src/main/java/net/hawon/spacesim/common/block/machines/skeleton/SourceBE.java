package net.hawon.spacesim.common.block.machines.skeleton;

import net.hawon.spacesim.common.network.Electricity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public abstract class SourceBE extends NodeBE {

    public Electricity e = new Electricity();

    public SourceBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
