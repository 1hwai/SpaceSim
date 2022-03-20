package net.hawon.spacesim.common.container;

import net.hawon.spacesim.common.block.entity.CrusherBlockEntity;
import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockInit;
import net.hawon.spacesim.core.Init.ContainerInit;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class CrusherContainer extends AbstractContainerMenu {
    private BlockEntity blockEntity;
    private Player playerEntity;
    private  IItemHandler playerInventory;


    public CrusherContainer(int id, BlockPos pos, Inventory playerInv, Player player) {
        super(ContainerInit.CRUSHER.get(), id);
        blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInv);

        final int slotSizePlus2 = 18;
        final int startX = 8, startY = 86, hotbarY = 144, inventoryY = 36;

        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, startX + 2 * slotSizePlus2, inventoryY));
                addSlot(new SlotItemHandler(handler, 1, startX + 6 * slotSizePlus2, inventoryY));
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
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockInit.CRUSHER.get());
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack item = slot.getItem();
            itemStack = item.copy();
            switch (index) {
                case 0:
                case 1:
                    if (!this.moveItemStackTo(item, 2, 38, true)) {
                        return ItemStack.EMPTY;
                    }
                    slot.onQuickCraft(item, itemStack);
                    break;
                default:
//                    if (CrusherBlockEntity.isItemValid(item)) {
                    if (true) {
                        if (!this.moveItemStackTo(item, 0, 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 29) {
                        if (!this.moveItemStackTo(item, 29, 38, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 38 && !this.moveItemStackTo(item, 2, 28, false)) {
                        return ItemStack.EMPTY;
                    }
            }
            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }
}
