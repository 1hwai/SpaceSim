package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class CrusherBlockEntity extends MachineBlockEntity {

    public static final Component TITLE = new TranslatableComponent("Crusher");

    public static final int ENERGY_CAPACITY = 800;
    public static final int MAX_TRANSFER = 1; //PER TICK
    public static final int MAX_EXTRACT = 1;

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CRUSHER.get(), pos, state, 2);
    }

    @Override
    public CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(ENERGY_CAPACITY, MAX_TRANSFER, MAX_EXTRACT) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    @Override
    public void tick() {
        receivePower();
    }

    @Override
    public void receivePower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() < ENERGY_CAPACITY) {
            for (Direction direction : Direction.values()) {
                BlockEntity be = level.getBlockEntity(worldPosition.relative(direction));
                if (be instanceof GeneratorBlockEntity) {
                    boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                                if (handler.canExtract()) {
                                    int received = handler.extractEnergy(Math.min(capacity.get(), ENERGY_CAPACITY), false);
                                    capacity.addAndGet(received);
                                    energyStorage.addEnergy(received);
                                    setChanged();
                                    return capacity.get() > 0;
                                } else {
                                    return true;
                                }
                            }
                    ).orElse(true);
                    if (!doContinue) {
                        return;
                    }
                } else if (be instanceof CableBlockEntity cableBE){
                    BlockEntity be0 = level.getBlockEntity(cableBE.sourcePos);
                    if (be0 instanceof GeneratorBlockEntity genBE) {
                        double loss = cableBE.distance * 0.05;
                        boolean doContinue = genBE.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                            if (handler.canExtract()) {
                                int received = handler.extractEnergy(Math.min(capacity.get(), ENERGY_CAPACITY), false);
                                capacity.addAndGet((int)(received - loss));
                                energyStorage.addEnergy((int)(received - loss));
                                setChanged();
                                return capacity.get() > 0;
                            } else {
                                return true;
                            }
                        }
                        ).orElse(true);
                        if (!doContinue) {
                            return;
                        }
                    }
                }
            }
        }

    }
}
