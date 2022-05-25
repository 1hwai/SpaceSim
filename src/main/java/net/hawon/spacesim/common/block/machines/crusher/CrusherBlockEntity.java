package net.hawon.spacesim.common.block.machines.crusher;

import net.hawon.spacesim.common.block.machines.MachineBlockEntity;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.machine.ServerMachinePacket;
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

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CRUSHER.get(), pos, state);
    }

    @Override
    public void tick() {
        if (timer == 0)
            PacketHandler.INSTANCE.sendToServer(new ServerMachinePacket(this.worldPosition));
        crush();
        timer++;
    }

    private void crush() {

    }

    private boolean isInputItemValid(ItemStack stack) {
        Item item = stack.getItem();
        if (ItemInit.RAW_TITANIUM.get().equals(item)) return true;
        if (Items.COAL.equals(item)) return true;
        if (Items.RAW_IRON.equals(item)) return true;

        return false;
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
