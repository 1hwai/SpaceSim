package net.hawon.spacesim.common.block.edges;

import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.hawon.spacesim.common.block.utils.SpaceBlockProperties.*;
import static net.hawon.spacesim.common.block.utils.SpaceBlockProperties.INV_WEST;

public class EdgeBlock extends Block {
    public EdgeBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(0.3f));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(
                UP, DOWN, NORTH, SOUTH, EAST, WEST,
                INV_UP, INV_DOWN, INV_NORTH, INV_SOUTH, INV_EAST, INV_WEST
        );
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState blockState = defaultBlockState();
        for (Direction direction : Direction.values()) {
            blockState = blockState.setValue(StateManager.attach(direction, true), false);
            blockState = blockState.setValue(StateManager.attach(direction, false), false);
        }

//        context.getClickedPos()

        return blockState;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
//        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        if (!level.isClientSide()) {
            StateManager.setState(level, pos);
        }

    }
}