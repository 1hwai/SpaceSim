package net.hawon.spacesim.common.item;

import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class RenchItem extends Item {

    public RenchItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide()) {
            BlockPos pos = pContext.getClickedPos();
            Player player = pContext.getPlayer();

            Block blockClicked = pContext.getLevel().getBlockState(pos).getBlock();
            if (isValuableBlock(blockClicked)) {
                outputValuableCoordinates(blockClicked, pos, player);
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(), (player) -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return super.useOn(pContext);
    }

    private void outputValuableCoordinates(Block block, BlockPos pos, Player player) {
        player.sendMessage(new TextComponent(block.asItem().getRegistryName().toString() + pos.toString()), player.getUUID());
    }

    private boolean isValuableBlock(Block block) {
        return block == BlockInit.GENERATOR.get();
    }
}

