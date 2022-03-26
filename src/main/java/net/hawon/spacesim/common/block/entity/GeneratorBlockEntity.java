package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.common.block.entity.util.InventoryBlockEntity;
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

public class GeneratorBlockEntity extends InventoryBlockEntity {

    public final int GEN_TIER = 1;

    public static final int GEN_CAPACITY = 100; //MAX ENERGY CAPACITY OF GENERATOR
    public static final int GEN_PER_TICK = 1;
    public static final int OUTPUT_PER_TICK = 1;

    public static final int MAX_TRANSFER = 1;
    public static final int MAX_EXTRACT = 1;

    private int counter;

    private final ItemStackHandler itemHandler;
    private final LazyOptional<IItemHandler> handler;

    private final CustomEnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energy;

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GENERATOR.get(), pos, state, 1);

        itemHandler = createHandler();
        handler = LazyOptional.of(() -> itemHandler);
        energyStorage = createEnergy();
        energy = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        energy.invalidate();
    }

    public void tickServer() {
        if (energyStorage.getEnergyStored() < GEN_CAPACITY) {
            if (counter > 0) {
                energyStorage.addEnergy(GEN_PER_TICK);
                counter--;
                setChanged();

            }
            if (counter <= 0) {
                ItemStack stack = itemHandler.getStackInSlot(0);
                if (isItemValid(stack)) {
                    int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
                    if (burnTime > 0) {
                        itemHandler.extractItem(0, 1, false);
                        counter = burnTime / 10;
                        setChanged();
                    }
                }
            }
        }

        BlockState blockState = level.getBlockState(worldPosition);
        if (blockState.getValue(BlockStateProperties.POWERED) != counter > 0) {
                level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, counter > 0),
                        Block.UPDATE_ALL);
        }

        //sendOutPower();
    }

    private static boolean isItemValid(ItemStack stack) {
        return stack.is(Items.COAL) || stack.is(Items.COAL_BLOCK);
    }

    private void sendOutPower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                BlockEntity be = level.getBlockEntity(worldPosition.relative(direction));
                if (be != null) {
                    if (be instanceof CableBlockEntity cableBE) {
                        cableBE.setSourcePos(worldPosition);
                        cableBE.setCurrent(OUTPUT_PER_TICK);
                    }
                    boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                        if (handler.canReceive()) {
                            int received = handler.receiveEnergy(Math.min(capacity.get(), OUTPUT_PER_TICK), false);
                            capacity.addAndGet(-received);
                            energyStorage.consumeEnergy(received);
                            setChanged();
                            return capacity.get() > 0;
                        } else {
                            return true;
                        }
                    }).orElse(true);
                    if (!doContinue) {
                        return;
                    }
                }
            }
        }
    }


    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(GEN_CAPACITY, MAX_TRANSFER, MAX_EXTRACT) {
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
                return GeneratorBlockEntity.this.isItemValid(stack);
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

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
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

    public int getTier() {
        return GEN_TIER;
    }

    public static int getOutputPerTick() {
        return OUTPUT_PER_TICK;
    }

}
