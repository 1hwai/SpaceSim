package net.hawon.spacesim.common.block.machines.crusher;

import net.hawon.spacesim.common.block.machines.MachineBlockEntity;
import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class CrusherBlockEntity extends MachineBlockEntity {

    public static final Component TITLE = new TranslatableComponent("Crusher");

    public static final int ENERGY_CAPACITY = 2000;
    public static final int MAX_TRANSFER = 0; //PER TICK
    public static final int MAX_EXTRACT = 0;
    public static final int ENERGY_CONSUME = 2; //PER TICK

    public static final int BURN_TIME = 200; //a.k.a. Burn Time, 10sec

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CRUSHER.get(), pos, state, 1, 1);
    }

    @Override
    public void tick() {
        if (timer == 0)
            setSource();
        receivePower();
        crush();
        timer++;
    }

    private void crush() {
        if (energyStorage.getEnergyStored() > 0) {
            if (progress > 0) {
                if (progress == 1) {
                    outputInv.insertItem(0, new ItemStack(ItemInit.TITANIUM_DUST.get()), false);
                }
                energyStorage.consumeEnergy(ENERGY_CONSUME);
                progress--;
            } else {
                if (isInputItemValid(inputInv.getStackInSlot(0))) {
                    inputInv.extractItem(0, 1, false);
                    progress = BURN_TIME + 1; //burn time, 10sec
                    setChanged();
                }
            }
        }
    }

    private boolean isInputItemValid(ItemStack stack) {
        Item item = stack.getItem();
        if (ItemInit.RAW_TITANIUM.get().equals(item)) return true;
        if (Items.COAL.equals(item)) return true;
        if (Items.RAW_IRON.equals(item)) return true;

        return false;
    }

    @Override
    public CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(ENERGY_CAPACITY, MAX_TRANSFER, MAX_EXTRACT) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }

            @Override
            public boolean canExtract()
            {
                return false;
            }
        };
    }

    @Override
    public ItemStackHandler createInputHandler() {
        return new ItemStackHandler(INPUT_SIZE) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return CrusherBlockEntity.this.isInputItemValid(stack);
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                CrusherBlockEntity.super.update();
                return super.extractItem(slot, amount, simulate);
            }

        };
    }

    @Override
    public ItemStackHandler createOutputHandler() {
        return new ItemStackHandler(OUTPUT_SIZE) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                CrusherBlockEntity.super.update();
                return super.insertItem(slot, stack, simulate);
            }

        };
    }


}
