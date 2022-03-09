 package net.hawon.spacesim.common.container;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.block.entity.ExampleChestBlockEntity;
import net.hawon.spacesim.common.block.entity.GeneratorBlockEntity;
import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockInit;
import net.hawon.spacesim.core.Init.ContainerInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class GeneratorContainer extends AbstractContainerMenu {

    private BlockEntity blockEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;

    public GeneratorContainer(int id, BlockPos pos, Inventory playerInv, Player player) {
        super(ContainerInit.GENERATOR.get(), id);
        IItemHandler slots = new ItemStackHandler(27);
        blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInv);

        final int slotSizePlus2 = 18;
        final int startX = 8, startY = 86, hotbarY = 144, inventoryY = 18;

        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, startX + 4 * slotSizePlus2, inventoryY));
            });
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv, 9 + row * 9 + column, startX + column * slotSizePlus2,
                        startY + row * slotSizePlus2));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv, column, startX + column * slotSizePlus2, hotbarY));
        }

        trackPower();
    }

    // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
    private void trackPower() {
        // splitting 32 bit integer into two 16 bit integers
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((CustomEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((CustomEnergyStorage)h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

    public int getEnergy() {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockInit.GENERATOR.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack item = slot.getItem();
            itemstack = item.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(item, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(item, itemstack);
            } else {
                if (ForgeHooks.getBurnTime(item, RecipeType.SMELTING) > 0) {
                    if (!this.moveItemStackTo(item, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.moveItemStackTo(item, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(item, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (item.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, item);
        }

        return itemstack;
    }

}
