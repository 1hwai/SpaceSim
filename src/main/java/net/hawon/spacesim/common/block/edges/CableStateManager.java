package net.hawon.spacesim.common.block.edges;

import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.block.nodes.skeleton.NodeBE;
import net.hawon.spacesim.common.block.utils.SpaceBlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import static net.hawon.spacesim.common.block.utils.SpaceBlockProperties.*;

public class CableStateManager {

    public static void setState(Level level, BlockPos pos) {

        BlockState blockState = level.getBlockState(pos);
        for (Direction direction : Direction.values()) {
            boolean attached = false;
            BlockEntity be = level.getBlockEntity(pos.relative(direction));
            if (be instanceof NodeBE || be instanceof CableBE) {
                attached = true;
            }
            blockState = blockState.setValue(attach(direction), attached);
        }
        if (level.getBlockState(pos) != blockState)
            level.setBlock(pos, blockState, Block.UPDATE_ALL);
    }

    public static BooleanProperty attach(Direction direction) {
        return switch (direction) {
            case UP -> UP;
            case DOWN -> DOWN;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
        };
    }

    public static String getConnections(BlockState state) {
        //Direction Order : UDNSEW
        //Never change this Order

        String status = (state.getValue(UP) ? "u" : "") + (state.getValue(DOWN) ? "d" : "")
                + (state.getValue(NORTH) ? "n" : "") + (state.getValue(SOUTH) ? "s" : "") + (state.getValue(EAST) ? "e" : "") + (state.getValue(WEST) ? "w" : "");
        if (status.equals("")) status = "none";

        return status;
    }

}
