package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.common.block.entity.util.InventoryBlockEntity;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleChestBlockEntity extends InventoryBlockEntity {

    public static final Component TITLE = new TranslatableComponent("Example Chest");

    public ExampleChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.EXAMPLE_CHEST.get(), pos, state, 27);
    }

}