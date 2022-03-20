package net.hawon.spacesim.common.block.entity.util;

import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MachineBlockEntity extends BlockEntity {

    public final int size;

    public int counter;

    public final ItemStackHandler itemHandler;
    public final LazyOptional<IItemHandler> handler;

    public final CustomEnergyStorage energyStorage;
    public final LazyOptional<IEnergyStorage> energy;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        if (size <= 0) size = 1;

        this.size = size;
        itemHandler = createHandler();
        handler = LazyOptional.of(() -> itemHandler);
        energyStorage = createEnergy();
        energy = LazyOptional.of(() -> energyStorage);

    }

    public abstract void tick();

    public abstract void receivePower();

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
        this.handler.invalidate();
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            this.itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("Energy")) {
            this.energyStorage.deserializeNBT(tag.get("Energy"));
        }
        if (tag.contains("Info")) {
            this.counter = tag.getCompound("Info").getInt("Counter");
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

    public abstract ItemStackHandler createHandler();

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Energy", energyStorage.serializeNBT());
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Counter", counter);
        tag.put("Info", infoTag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

}
