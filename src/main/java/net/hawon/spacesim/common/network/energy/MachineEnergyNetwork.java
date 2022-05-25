package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.machines.MachineBlockEntity;
import net.hawon.spacesim.common.block.machines.SourceBlockEntity;
import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MachineEnergyNetwork {

    private final Level level;
    private final MachineBlockEntity machineBE;

    public MachineEnergyNetwork(Level level, MachineBlockEntity machineBE) {
        this.level = level;
        this.machineBE = machineBE;
    }

    public void updateSource() {
        boolean isConnected = false;
        for (Direction direction : Direction.values()) {
            BlockPos pos = machineBE.getBlockPos().relative(direction);
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof CableBlockEntity cableBE) {
                machineBE.setSource(cableBE.source);
                SourceBlockEntity sourceBE = machineBE.getSource();
                sourceBE.checkIn(machineBE.id, machineBE);
                isConnected = true;
            }
        }
        if (!isConnected)
            machineBE.setSource(null);
    }

}
