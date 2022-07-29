package net.hawon.spacesim.common.block.nodes.generator;

import net.hawon.spacesim.common.block.nodes.skeleton.SourceBE;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class TestgenBE extends SourceBE {

    private int timer;

    public TestgenBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.TESTGEN.get(), pos, state);
    }

    public void tick()  {
        timer++;
    }


}
