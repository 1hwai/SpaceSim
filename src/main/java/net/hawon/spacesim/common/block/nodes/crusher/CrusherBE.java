package net.hawon.spacesim.common.block.nodes.crusher;

import net.hawon.spacesim.common.block.nodes.MachineBE;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class CrusherBE extends MachineBE {

    public static final Component TITLE = new TranslatableComponent("addServer.resourcePack");

    public CrusherBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CRUSHER.get(), pos, state);
    }

    public void tick() {
        timer++;
    }

    private void crush() {
//        PacketHandler.INSTANCE.sendToServer(new ServerMachinePacket(this.worldPosition));
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
                return CrusherBE.this.isInputItemValid(stack);
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                CrusherBE.super.update();
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
            public @NotNull ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                CrusherBE.super.update();
                return super.insertItem(slot, stack, simulate);
            }

        };
    }


}
