package net.hawon.spacesim.common.block.edges.cables;

import net.hawon.spacesim.common.block.edges.EdgeBlock;
import net.hawon.spacesim.common.block.edges.StateManager;
import net.hawon.spacesim.common.block.nodes.skeleton.NodeBE;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class CopperCableBlock extends EdgeBlock implements EntityBlock {

    public CopperCableBlock() {
        super();
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
//        return makeShape(state);
//    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBE(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState state, BlockEntityType<T> beType) {
        if (!level.isClientSide()) {
            return (level0, pos, state0, blockEntity) -> {
                if (blockEntity instanceof CableBE cableBE) {
                    cableBE.tick();
                }
            };
        }

        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

//        if (!level.isClientSide())
//            PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(pos));
    }

    @Override
    public void destroy(@NotNull LevelAccessor levelAccessor, BlockPos pos, BlockState state) {
        super.destroy(levelAccessor, pos, state);
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(pos));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof CableBE cable) {
                Item item = player.getItemInHand(hand).getItem();
                if (item.asItem() == ItemInit.GALVANOMETER.get()) {
                    cable.find();
                }
            }
        }
        return InteractionResult.FAIL;
    }

    public static VoxelShape makeShape(BlockState state) {
        VoxelShape shape = Shapes.empty();
        switch (StateManager.getConnections(state)) {
            default -> shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.4375, 0.5625, 0.5625, 0.5625), BooleanOp.OR);
        }
        return shape;
    }
}