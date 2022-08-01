package net.hawon.spacesim.common.block.edges.cables;

import net.hawon.spacesim.common.block.edges.CableStateManager;
import net.hawon.spacesim.common.block.edges.EdgeBlock;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
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

public class CableBlock extends EdgeBlock implements EntityBlock {

    public final CableType type;

    public CableBlock(CableType type) {
        super();
        this.type = type;
    }

    public CableType getType() {
        return type;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
    }

    @Override
    public void destroy(@NotNull LevelAccessor levelAccessor, BlockPos pos, BlockState state) {
        super.destroy(levelAccessor, pos, state);
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(pos));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState blockState = defaultBlockState();
        for (Direction direction : Direction.values()) {
            blockState = blockState.setValue(CableStateManager.attach(direction), false);
        }

        return blockState;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        if (!level.isClientSide()) {
            CableStateManager.setState(level, pos);
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return makeShape(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof CableBE cable) {
                Item item = player.getItemInHand(hand).getItem();
                if (item.asItem() == ItemInit.GALVANOMETER.get()) {
                }
            }
        }
        return InteractionResult.FAIL;
    }

    public static VoxelShape makeShape(BlockState state) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.4375, 0.5625, 0.5625, 0.5625), BooleanOp.OR);

        switch (CableStateManager.getConnections(state)) {
            case "udnsew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udnse" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udnsw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udns" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
            }
            case "udnew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udne" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udnw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udn" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
            }
            case "udsew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udse" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udsw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "uds" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
            }
            case "udew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "ude" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "udw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "ud" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
            }
            case "unsew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "unse" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "unsw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "uns" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
            }
            case "unew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "une" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "unw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "un" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
            }
            case "usew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "use" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "usw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "us" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
            }
            case "uew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "ue" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "uw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "u" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
            }
            case "dnsew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dnse" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dnsw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dns" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
            }
            case "dnew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dne" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dnw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dn" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
            }
            case "dsew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dse" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dsw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "ds" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
            }
            case "dew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "de" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "dw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "d" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.4375, 0.5625), BooleanOp.OR);
            }
            case "nsew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "nse" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "nsw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "ns" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
            }
            case "new" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "ne" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "nw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "n" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.4375), BooleanOp.OR);
            }
            case "sew" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "se" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "sw" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "s" -> {
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 1), BooleanOp.OR);
            }
            case "ew" -> {
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "e" -> {
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.4375, 0.4375, 1, 0.5625, 0.5625), BooleanOp.OR);
            }
            case "w" -> {
                shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625), BooleanOp.OR);
            }
        }
        return shape;
    }

}
