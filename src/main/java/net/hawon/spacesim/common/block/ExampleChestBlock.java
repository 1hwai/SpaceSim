package net.hawon.spacesim.common.block;

import net.hawon.spacesim.common.block.entity.ExampleChestBlockEntity;
import net.hawon.spacesim.common.container.ExampleChestContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class ExampleChestBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public ExampleChestBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState state, BlockEntityType<T> beType) {
        if (!level.isClientSide) {
            return (level0, pos, state0, blockEntity) -> ((ExampleChestBlockEntity) blockEntity).tick();
        }

        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ExampleChestBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult result) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof final ExampleChestBlockEntity chest) {
            final MenuProvider container = new SimpleMenuProvider(ExampleChestContainer.getServerContainer(chest, pos),
                    ExampleChestBlockEntity.TITLE);
            NetworkHooks.openGui((ServerPlayer) player, container, pos);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}