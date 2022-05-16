package net.hawon.spacesim.common.block.generator;

import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorBlockEntity extends BlockEntity {

    public final int GEN_TIER = 1;

    public static final int ENERGY_CAPACITY = 8000 * 1000; //MAX ENERGY CAPACITY OF GENERATOR
    public static final int GEN_PER_TICK = 1000;




    public static final int MAX_TRANSFER = 1;
    public static final int MAX_EXTRACT = 1;

    private int progress;

    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> handler;

    private final CustomEnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energy;

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GENERATOR.get(), pos, state);

        inventory = createHandler();
        handler = LazyOptional.of(() -> inventory);
        energyStorage = createEnergy();
        energy = LazyOptional.of(() -> energyStorage);
    }

    public void tickServer() {
        if (energyStorage.getEnergyStored() < ENERGY_CAPACITY) {
            if (progress > 0) {
                energyStorage.addEnergy(GEN_PER_TICK);
                progress--;
                setChanged();

            }
            if (progress <= 0) {
                ItemStack stack = inventory.getStackInSlot(0);
                if (isItemValid(stack)) {
                    int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
                    if (burnTime > 0) {
                        inventory.extractItem(0, 1, false);
                        progress = burnTime / 10;
                        setChanged();
                    }
                }
            }
        }

        BlockState blockState = level.getBlockState(worldPosition);
        if (blockState.getValue(BlockStateProperties.POWERED) != progress > 0) {
                level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, progress > 0),
                        Block.UPDATE_ALL);
        }
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
        this.handler.invalidate();
        this.energy.invalidate();
    }

    private static boolean isItemValid(ItemStack stack) {
        return stack.is(Items.COAL) || stack.is(Items.COAL_BLOCK);
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(ENERGY_CAPACITY, MAX_TRANSFER, MAX_EXTRACT) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return GeneratorBlockEntity.isItemValid(stack);
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            this.inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("Energy")) {
            this.energyStorage.deserializeNBT(tag.get("Energy"));
        }
        if (tag.contains("Info")) {
            this.progress = tag.getCompound("Info").getInt("progress");
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

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", inventory.serializeNBT());
        tag.put("Energy", energyStorage.serializeNBT());
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("progress", progress);
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

    public int getTier() {
        return GEN_TIER;
    }

    public static int getMaxExtract() {
        return MAX_EXTRACT;
    }

}
