package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CopperCableBlockEntity extends CableEntity {

    public CopperCableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.COPPER_CABLE.get(), pos, state);
        this.MAX_CURRENT = 30;
    }

}
