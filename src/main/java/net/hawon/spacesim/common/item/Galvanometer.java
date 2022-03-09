package net.hawon.spacesim.common.item;

import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class Galvanometer extends Item {

    public Galvanometer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) {
            Level level = pContext.getLevel();
            BlockPos pos = pContext.getClickedPos();

            BlockState state = level.getBlockState(pos);

            Player player = pContext.getPlayer();

//            if (isValuableBlock(state)) {
//                outputValuableCoordinates(state, player);
//            }
        }

        return super.useOn(pContext);
    }

    private void outputValuableCoordinates(BlockState state, Player player) {
        player.sendMessage(new TextComponent(state.getValue(BlockStateProperties.POWER).toString()), player.getUUID());

    }

    private boolean isValuableBlock(BlockState state) {
        return state.hasProperty(BlockStateProperties.POWER);
    }
}
