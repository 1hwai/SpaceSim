package net.hawon.spacesim.common.block.machines.generator.GeneratorOM;

import net.hawon.spacesim.common.block.machines.generator.GeneratorCC.GeneratorCCBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GeneratorOMBlockEntity extends BlockEntity {
    //Generator Output Manager
    //Managing all the machines linked into Generator
    //May not exist in real, but just for faster Networking

    private int timer;

    public GeneratorCCBlockEntity gccBE;

    public GeneratorOMBlockEntity(BlockPos pos, BlockState state) {
        super(p_155228_, pos, state);
    }


}
