package net.hawon.spacesim.common.block.pipe;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import static net.hawon.spacesim.common.block.utils.SpaceBlockProperties.*;

public abstract class PipeBlock extends Block {

    public PipeBlock() {
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = defaultBlockState();
        for (Direction direction : Direction.values()) {
            blockState = blockState.setValue(StateManager.attach(direction, true), false);
            blockState = blockState.setValue(StateManager.attach(direction, false), false);
        }

        return blockState;
    }

}