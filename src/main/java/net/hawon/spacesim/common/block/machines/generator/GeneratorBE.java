package net.hawon.spacesim.common.block.machines.generator;

import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.network.Electricity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class GeneratorBE extends SourceBE {

    public GeneratorBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
