package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.generator.GeneratorBlockEntity;
import net.hawon.spacesim.common.block.machines.MachineBlockEntity;
import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class MachineEnergyNetwork {

    private final Level level;
    private final MachineBlockEntity machineBE;

    public MachineEnergyNetwork(Level level, MachineBlockEntity machineBE) {
        this.level = level;
        this.machineBE = machineBE;
    }

    public void updateSource() {
        float maxCurrent = 0;
        boolean isConnected = false;
        for (Direction direction : Direction.values()) {
            BlockPos pos = machineBE.getBlockPos().relative(direction);
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof CableBlockEntity cableBE) {
                if (cableBE.getCurrent() > maxCurrent) {
                    maxCurrent = cableBE.getCurrent();
                    machineBE.setSourcePos(cableBE.getSourcePos());
                    machineBE.setCurrent(cableBE.getCurrent());
                    isConnected = true;
                }
            } else if (be.getCapability(CapabilityEnergy.ENERGY, null).isPresent()) {
                machineBE.setSourcePos(pos);
            }
        }
        if (!isConnected)
            machineBE.setSourcePos(null);


    }

    public boolean isReceivable() {

    }

}
