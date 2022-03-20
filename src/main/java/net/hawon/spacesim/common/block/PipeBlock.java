package net.hawon.spacesim.common.block;

import net.hawon.spacesim.common.network.pipe.StateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.hawon.spacesim.common.block.entity.util.SpaceBlockProperties.*;

public abstract class PipeBlock extends Block implements EntityBlock {

    public PipeBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(0.3f));
    }

    @Nullable
    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(
                UP, DOWN, NORTH, SOUTH, EAST, WEST,
                INV_UP,INV_DOWN, INV_NORTH, INV_SOUTH, INV_EAST, INV_WEST
        );
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = defaultBlockState();
        for (Direction direction : Direction.values()) {
            blockState = blockState.setValue(StateManager.attach(direction, true), false);
            blockState = blockState.setValue(StateManager.attach(direction, false), false);
        }

        return blockState;
    }

    @Override
    public abstract <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState state, BlockEntityType<T> beType);

    @SuppressWarnings("deprecation")
    @Override
    public abstract InteractionResult use(BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result);





}
