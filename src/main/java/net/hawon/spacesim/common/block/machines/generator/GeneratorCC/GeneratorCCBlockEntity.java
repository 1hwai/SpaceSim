package net.hawon.spacesim.common.block.machines.generator.GeneratorCC;

import net.hawon.spacesim.common.energy.Electricity;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

public class GeneratorCCBlockEntity extends BlockEntity {
    //Generator Control Computer //Also a Monitor

    private int timer;

    public BlockEntity electromagnetBE;

    public int rpm = 3000, eMagnetStrength = 80;
    //eMagnetStrength as percentage
    //average rpm should be 3600

    public Electricity e = new Electricity();
    //average output

    public GeneratorCCBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GENERATOR_CC.get(), pos, state);
    }

    public void tick() {
        setElectricity();
        timer++;
    }

    private void setElectricity() {
        double strength = rpm * eMagnetStrength;
        if (!isIdle()) {
            e.setCurrent(0.125 * strength);
            if (strength > 0) {
                if (strength < 55000)
                    e.setVoltage(0.4 * rpm * eMagnetStrength);
                else
                    e.setVoltage(22000);
            }
        } else {
            e.reset();
        }
    }

    public boolean isIdle() {
        return rpm <= 0 || eMagnetStrength <= 0;
    }

}
