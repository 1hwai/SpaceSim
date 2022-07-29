package net.hawon.spacesim.common.item;

import net.hawon.spacesim.common.block.nodes.skeleton.NodeBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class RenchItem extends Item {

    public RenchItem(Properties pProperties) {
        super(pProperties);
    }

    public static InteractionResult rotate(BlockState state, Level level, BlockPos pos, Player player) {
        state = state.setValue(FACING, player.getDirection().getOpposite());
        level.setBlock(pos, state, Block.UPDATE_ALL);
        if (level.getBlockEntity(pos) instanceof NodeBE nodeBE)
            nodeBE.rotate(state);

        return InteractionResult.FAIL;
    }
}

