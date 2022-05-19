package net.hawon.spacesim.common.block.machines.generator.GeneratorOM;

import net.hawon.spacesim.common.block.machines.MachineBlockEntity;
import net.hawon.spacesim.common.block.machines.generator.GeneratorCC.GeneratorCCBlockEntity;
import net.hawon.spacesim.common.energy.ThreePhaseType;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.UUID;

public class GeneratorOMBlockEntity extends BlockEntity {
    //Generator Output Manager
    //Managing all the machines linked into Generator
    //May not exist in real, but just for faster Networking

    private int timer;

    public GeneratorCCBlockEntity gccBE;
    public ThreePhaseType phaseType;

    public double powerUsage = 0;

    private final HashMap<UUID, MachineBlockEntity> load = new HashMap<>();
    //This connectionMap manages machines connected to the generator, to manage its usage

    public GeneratorOMBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GENERATOR_OM_L1.get(), pos, state);
    }

    public GeneratorOMBlockEntity(BlockPos pos, BlockState state, ThreePhaseType phaseType) {
        this(pos, state);
        this.phaseType = phaseType;

        findGCC();
    }

    public void tick() {

        timer++;
    }

    protected void findGCC() {
        boolean isConnected = false;
        for (Direction direction : Direction.values()) {
            BlockPos pos = worldPosition.relative(direction);
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof GeneratorCCBlockEntity gccBE) {
                this.gccBE = gccBE;
                isConnected = true;
                break;
            }
        }
        if (!isConnected)
            this.gccBE = null;
    }

    public void checkIn(UUID id, MachineBlockEntity machineBE) {

    }

    public void checkOut(UUID id) {

    }

    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (level != null) {
            level.setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

}
