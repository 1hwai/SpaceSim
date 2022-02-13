package net.hawon.spacesim.common.item;

import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class RenchItem extends Item {

    public RenchItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide()) {
            Level level = pContext.getLevel();
            BlockPos pos = pContext.getClickedPos();
            Player player = pContext.getPlayer();

            BlockState state = level.getBlockState(pos); //current block state

            Block blockClicked = state.getBlock();

            if (isValuableBlock(blockClicked)) {
                outputValuableCoordinates(blockClicked, pos, player, pContext);
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                (player) -> player.broadcastBreakEvent(player.getUsedItemHand())
        );

        return super.useOn(pContext);
    }

    private void outputValuableCoordinates(Block block, BlockPos pos, Player player, UseOnContext pContext) {
        player.sendMessage(new TextComponent(block.asItem().getRegistryName().toString() + pos.toString()/*Here*/), player.getUUID());

    }

//    private static BlockState rotateHorizontalBlock(Level level, BlockPos pos, BlockState state) {
//
//    }

    private boolean isValuableBlock(Block block) {
        return block == BlockInit.GENERATOR.get();
    }
}

