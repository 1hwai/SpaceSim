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
    public HashMap<Integer, BlockEntity> connectionMap = new HashMap<>();
    //This connectionMap manages machines connected to the generator, to manage its usage

    public int rpm = 3000, eMagnetStrength = 80;
    //eMagnetStrength as percentage
    //average rpm should be 3600


    public Electricity electricity = new Electricity(); //average output

    public GeneratorCCBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GENERATOR_CC.get(), pos, state);
    }

    public void tick() {
        electricity.setCurrent(14 * rpm * eMagnetStrength); //almost equals (eMS + eMS/3) * 10 * rpm
        electricity.power = electricity.getCurrent() * electricity.getVoltageLN();
        if (timer % 80 == 0)
            printInfo();
        timer++;
    }

    public void printInfo() {
        System.out.println("RPM : " + rpm + " EMS: " + eMagnetStrength);
        System.out.println(
                "Current : " + electricity.getCurrent()
                + "LN Voltage(abs) : " + electricity.getVoltageLN()
                + "LL Voltage(abs) : " + electricity.getVoltageLL()
                + " Power : " + electricity.getPower()
                );
    }


    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (level != null) {
            level.setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

}
