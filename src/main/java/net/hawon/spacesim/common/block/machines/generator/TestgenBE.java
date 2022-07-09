package net.hawon.spacesim.common.block.machines.generator;

import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TestgenBE extends SourceBE {


    public TestgenBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.TESTGEN.get(), pos, state);
    }

    public void tick()  {

    }


}
