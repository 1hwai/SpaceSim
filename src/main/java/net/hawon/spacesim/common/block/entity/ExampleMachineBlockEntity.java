package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class ExampleMachineBlockEntity extends InventoryBlockEntity {

    public static final int ENERGY_CAPACITY = 5000;

    private int counter;

    public final CustomEnergyStorage energyStorage = createEnergy();
    public final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    public ExampleMachineBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.EXAMPLE_MACHINE.get(), pos, state, 27);
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(ENERGY_CAPACITY, 20, 20) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    public void tickServer() {
        if (counter == 40) counter = 0;
        sendOutPower();

        counter++;
    }

    private void sendOutPower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                BlockEntity be = level.getBlockEntity(worldPosition.relative(direction));
                if (be != null) {
                    boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                            if (handler.canExtract()) {
                                int received = handler.extractEnergy(Math.min(capacity.get(), ENERGY_CAPACITY), false);
                                capacity.addAndGet(-received);
                                energyStorage.consumeEnergy(received);
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


    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Energy")) {
            this.energyStorage.deserializeNBT(tag.get("Energy"));
        }
        if (tag.contains("Info")) {
            this.counter = tag.getCompound("Info").getInt("Counter");
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Energy", energyStorage.serializeNBT());
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Counter", counter);
        tag.put("Info", infoTag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

}
