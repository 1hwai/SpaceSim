package net.hawon.spacesim.common.block.nodes;

import net.hawon.spacesim.common.block.nodes.skeleton.ConsumerBE;
import net.hawon.spacesim.common.network.Electricity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class MachineBE extends ConsumerBE {

    protected int timer;
    public int progress;

    public Electricity e = new Electricity();

    public final int INPUT_SIZE;
    public final int OUTPUT_SIZE;
    public final ItemStackHandler inputInv;
    public final LazyOptional<IItemHandler> inputHandler;
    public final ItemStackHandler outputInv;
    public final LazyOptional<IItemHandler> outputHandler;

    public MachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state, int inputSize, int outputSize) {
        super(type, pos, state);

        e.regular = new Electricity(0.2, 220, 1100);

        INPUT_SIZE = inputSize;
        OUTPUT_SIZE = outputSize;

        inputInv = createInputHandler();
        inputHandler = LazyOptional.of(() -> inputInv);
        outputInv = createOutputHandler();
        outputHandler = LazyOptional.of(() -> outputInv);

    }

    public MachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, 1, 1);
    }

    //Default Settings

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
        inputHandler.invalidate();
        outputHandler.invalidate();
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("inputInventory")) {
            inputInv.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("outputInventory")) {
            outputInv.deserializeNBT(tag.getCompound("outputInventory"));
        }
        if (tag.contains("Info")) {
            progress = tag.getCompound("Info").getInt("Progress");
        }
        super.load(tag);
    }

    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (level != null) {
            level.setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

    public abstract ItemStackHandler createInputHandler();
    public abstract ItemStackHandler createOutputHandler();

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inputInventory", inputInv.serializeNBT());
        tag.put("outputInventory", outputInv.serializeNBT());

        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Progress", progress);
        tag.put("Info", infoTag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == Direction.UP) {
            return inputHandler.cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == Direction.DOWN) {
            return outputHandler.cast();
        }
        return super.getCapability(cap, side);
    }

}
