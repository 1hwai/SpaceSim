package net.hawon.spacesim.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;

public class ElectricFurnaceBlockEntity extends InventoryBlockEntity {

    public static final int MAX_CURRENT = 10;

    public ElectricFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, 27);
    }

    public void tickServer() {
        if (energyStorage.getEnergyStored() <= GEN_CAPACTITY) {
            if (counter > 0) {
                energyStorage.addEnergy(GEN_PER_TICK);
                counter--;
                setChanged();

            } else if (counter <= 0) {
                ItemStack stack = itemHandler.getStackInSlot(0);
                int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
                if (burnTime > 0) {
                    itemHandler.extractItem(0, 1, false);
                    counter = burnTime / 20;
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



}
