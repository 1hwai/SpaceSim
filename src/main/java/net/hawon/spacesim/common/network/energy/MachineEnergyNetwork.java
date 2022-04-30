package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

public class MachineEnergyNetwork {

    private Level level;

    public MachineEnergyNetwork(Level level) {
        this.level = level;
    }

    public void updateSource(BlockPos worldPosition) {

    }

    public void receivePower() {

    }

}
