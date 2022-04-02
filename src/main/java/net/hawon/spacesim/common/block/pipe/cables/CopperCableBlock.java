package net.hawon.spacesim.common.block.pipe.cables;

import net.hawon.spacesim.common.block.pipe.PipeBlock;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.ServerEnergyPacket;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CopperCableBlock extends PipeBlock implements EntityBlock {

    public CopperCableBlock() {
        super();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state, 0);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState state, BlockEntityType<T> beType) {
        if (!level.isClientSide()) {
            return (level0, pos, state0, blockEntity) -> {
                if (blockEntity instanceof CableBlockEntity be) {
                    be.tickServer();
                }
            };
        }

        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        if (!level.isClientSide()) {
            StateManager.setState(level, pos);
            PacketHandler.INSTANCE.sendToServer(new ServerEnergyPacket(pos));
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof CableBlockEntity cable) {
                Item item = player.getItemInHand(hand).getItem();
                if (item.asItem() == ItemInit.GALVANOMETER.get()) {
                    System.out.println(cable.getSourcePos() + " | " + cable.getCurrent());
                }
            }
        }
        return InteractionResult.FAIL;
    }
}