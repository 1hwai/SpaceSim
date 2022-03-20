package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.common.block.entity.util.CableBlockEntity;
import net.hawon.spacesim.common.block.entity.util.MachineBlockEntity;
import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

public class CrusherBlockEntity extends MachineBlockEntity {

    public static final Component TITLE = new TranslatableComponent("Crusher");

    public static final int ENERGY_CAPACITY = 80;
    public static final int MAX_TRANSFER = 1; //PER TICK
    public static final int MAX_EXTRACT = 1;
    public static final int ENERGY_CONSUME = 2; //PER 5 TICK

    public static final int DELTA_TIME = 200; //a.k.a. Burn Time, 10sec

    public ItemStack stack0;

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CRUSHER.get(), pos, state, 2);
    }

    @Override
    public void tick() {
        receivePower();
        crush();
    }

    private void crush() {
        if (energyStorage.getEnergyStored() > 0) {
            if (counter > 0) {
                if (counter == 1) {
                    System.out.println("counter = 1 !!!!!!!!");
                    ItemStack itemStack = ItemInit.TITANIUM_INGOT.get().getDefaultInstance();
                    System.out.println(itemStack);
                    itemHandler.insertItem(1, itemStack, false);
                }
                if (counter % 5 == 0) {
                    energyStorage.consumeEnergy(ENERGY_CONSUME);
                }
                counter--;
            } else {
                stack0 = itemHandler.getStackInSlot(0);
                if (isItemValid(stack0)) {
                    itemHandler.extractItem(0, 1, false);
                    counter = DELTA_TIME + 1; //burn time, 10sec
                    setChanged();
                }
            }
        }

        receivePower();
    }

    @Override
    public ItemStackHandler createHandler() {
        return new ItemStackHandler(this.size) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return CrusherBlockEntity.this.isItemValid(stack);
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                CrusherBlockEntity.super.update();
                return super.extractItem(slot, amount, simulate);
            }

            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0) {
                    return stack;
                }
                CrusherBlockEntity.super.update();
                return super.insertItem(slot, stack, simulate);
            }

        };
    }

    private boolean isItemValid(ItemStack stack) {
        Item item = stack.getItem();
        if (ItemInit.RAW_TITANIUM.get().equals(item)) return true;
        if (Items.COAL.equals(item)) return true;
        if (Items.RAW_IRON.equals(item)) return true;

        return false;
    }

    @Override
    public void receivePower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() < ENERGY_CAPACITY) {
            for (Direction direction : Direction.values()) {
                BlockEntity be = level.getBlockEntity(worldPosition.relative(direction));
                if (be instanceof CableBlockEntity cableBE) {
                    if (cableBE.getSourcePos() != null) {
                        BlockEntity be0 = level.getBlockEntity(cableBE.getSourcePos());
                        if (be0 instanceof GeneratorBlockEntity genBE) {
                            boolean doContinue = genBE.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                                if (handler.canExtract()) {
                                    int extracted = handler.receiveEnergy(Math.min(capacity.get(), GeneratorBlockEntity.GEN_OUTPUT_PER_TICK), false);
                                    capacity.addAndGet(extracted);
                                    energyStorage.addEnergy(extracted);
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
        }
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



}
