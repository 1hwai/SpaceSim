package net.hawon.spacesim.common.block.machines;

import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.hawon.spacesim.common.block.generator.GeneratorBlockEntity;
import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MachineBlockEntity extends BlockEntity {

    protected int timer;
    public int progress;

    public BlockPos sourcePos;
    private float current;

    public final int INPUT_SIZE;
    public final int OUTPUT_SIZE;

    public final ItemStackHandler inputInv;
    public final LazyOptional<IItemHandler> inputHandler;
    public final ItemStackHandler outputInv;
    public final LazyOptional<IItemHandler> outputHandler;

    public int ENERGY_CAPACITY;
    public int MIN_CURRENT = 16;
    public final CustomEnergyStorage energyStorage;
    public final LazyOptional<IEnergyStorage> energy;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inputSize, int outputSize, int energyCapacity) {
        super(type, pos, state);

        INPUT_SIZE = inputSize;
        OUTPUT_SIZE = outputSize;
        ENERGY_CAPACITY = energyCapacity * 1000; //Important: KILO

        inputInv = createInputHandler();
        inputHandler = LazyOptional.of(() -> inputInv);
        outputInv = createOutputHandler();
        outputHandler = LazyOptional.of(() -> outputInv);

        energyStorage = createEnergy();
        energy = LazyOptional.of(() -> energyStorage);

    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, 1, 1, 16000);
    }

    public abstract void tick();

    public void receivePower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (sourcePos != null && capacity.get() < ENERGY_CAPACITY && current > MIN_CURRENT) {
            BlockEntity be = level.getBlockEntity(sourcePos);
            if (be != null) {
                be.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(storage -> {
                    if (storage.canExtract()) {
                        int extracted = storage.extractEnergy(GeneratorBlockEntity.MAX_EXTRACT, false);
                        energyStorage.addEnergy((int) Math.min(extracted, current));
                        update();
                    }
                });
            }
        }
    }

    public void setSource() {
        float maxCurrent = 0;
        boolean zeroConnection = true;
        for (Direction direction : Direction.values()) {
            BlockPos pos = worldPosition.relative(direction);
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof CableBlockEntity cableBE) {
                if (cableBE.getCurrent() > maxCurrent) {
                    maxCurrent = cableBE.getCurrent();
                    sourcePos = cableBE.getSourcePos();
                    current = cableBE.getCurrent();//!!!!!!!!!!!!
                    zeroConnection = false;
                }
            } else if (be instanceof GeneratorBlockEntity) {
                sourcePos = pos;
                current = GeneratorBlockEntity.getMaxExtract();
            }
        }
        if (zeroConnection) sourcePos = null;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.inputHandler.invalidate();
        this.outputHandler.invalidate();
        this.energy.invalidate();
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("inputInventory")) {
            this.inputInv.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("outputInventory")) {
            this.outputInv.deserializeNBT(tag.getCompound("outputInventory"));
        }
        if (tag.contains("Energy")) {
            this.energyStorage.deserializeNBT(tag.get("Energy"));
        }
        if (tag.contains("Info")) {
            this.progress = tag.getCompound("Info").getInt("Counter");
        }
        super.load(tag);
    }

    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
        }
    }

    public abstract CustomEnergyStorage createEnergy();

    public abstract ItemStackHandler createInputHandler();
    public abstract ItemStackHandler createOutputHandler();

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inputInventory", inputInv.serializeNBT());
        tag.put("outputInventory", outputInv.serializeNBT());
        tag.put("Energy", energyStorage.serializeNBT());

        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Progress", progress);
        tag.put("Info", infoTag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == Direction.UP) {
            return inputHandler.cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == Direction.DOWN) {
            return outputHandler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

}
