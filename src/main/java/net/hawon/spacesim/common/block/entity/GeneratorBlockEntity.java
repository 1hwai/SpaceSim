package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
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

    public static final int GEN_CAPACTITY = 2000; //MAX ENERGY CAPACITY OF GENERATOR
    public static final int GEN_PER_TICK = 1;
    public static final int GEN_OUTPUT_PER_TICK = 20;

    private int counter;

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private final CustomEnergyStorage energyStorage = createEnergy();
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    public static final Component TITLE = new TranslatableComponent("Generator");

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GENERATOR.get(), pos, state, 27);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        energy.invalidate();
    }

    public void tickServer() {
        if (energyStorage.getEnergyStored() <= GEN_CAPACTITY) {
            if (counter > 0) {
                energyStorage.addEnergy(GEN_PER_TICK);
                counter--;
                setChanged();
            }

            if (counter <= 0) {
                ItemStack stack = itemHandler.getStackInSlot(0);
                int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
                if (burnTime > 0) {
                    itemHandler.extractItem(0, 1, false);
                    counter = burnTime / 5;
                    setChanged();
                }
            }
        }

        BlockState blockState = level.getBlockState(worldPosition);
        if (blockState.getValue(BlockStateProperties.POWERED) != counter > 0) {
            level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, counter > 0),
                    Block.UPDATE_ALL);
        }

        sendOutPower();
    }

    private void sendOutPower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                BlockEntity be = level.getBlockEntity(worldPosition.relative(direction));
                if (be != null) {
                    boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                            if (handler.canReceive()) {
                                int received = handler.receiveEnergy(Math.min(capacity.get(), GEN_OUTPUT_PER_TICK), false);
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
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.put("Energy", energyStorage.serializeNBT());

        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Counter", counter);
        tag.put("Info", infoTag);
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(GEN_CAPACTITY, 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(27) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
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
