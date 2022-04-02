package net.hawon.spacesim.common.block.pipe;

import net.hawon.spacesim.common.block.pipe.cables.StateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.hawon.spacesim.common.block.util.SpaceBlockProperties.*;

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

    public abstract static class PipeBlockEntity extends BlockEntity {

        public PipeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
            super(type, pos, state);
        }

        @Override
        public final CompoundTag getUpdateTag() {
            return writeUpdate(super.getUpdateTag());
        }

        @Override
        public final ClientboundBlockEntityDataPacket getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
        }

        public CompoundTag writeUpdate(CompoundTag tag) {
            return tag;
        }

    }
}
