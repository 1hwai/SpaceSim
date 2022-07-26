package net.hawon.spacesim.common.block.edges;

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

public class StateManager {

    public static void setState(Level level, BlockPos pos) {

        BlockState blockState = level.getBlockState(pos);
        for (Direction direction : Direction.values()) {
            BlockEntity be = level.getBlockEntity(pos.relative(direction));
            boolean[] attachment = new boolean[2];

            if (be instanceof NodeBE) {
                attachment[0] = true;
            } else if (be instanceof EdgeBE) { // Fix required
                attachment[1] = true;
            }
            blockState = blockState.setValue(attach(direction, true), attachment[0]);
            blockState = blockState.setValue(attach(direction, false), attachment[1]);

        }

        level.setBlock(pos, blockState, Block.UPDATE_ALL);
    }

    public static BooleanProperty attach(Direction direction, Boolean inv) {
        if (inv) { //inv = true;
            return switch (direction) {
                case UP -> INV_UP;
                case DOWN -> INV_DOWN;
                case NORTH -> INV_NORTH;
                case SOUTH -> INV_SOUTH;
                case EAST -> INV_EAST;
                case WEST -> INV_WEST;
            };
        } else {
            return switch (direction) {
                case UP -> UP;
                case DOWN -> DOWN;
                case NORTH -> NORTH;
                case SOUTH -> SOUTH;
                case EAST -> EAST;
                case WEST -> WEST;
            };
        }
    }

    public static int getConnections(BlockState state) {
        int statusCode = 0;
        //Never change this Order
        int up = state.getValue(INV_UP) || state.getValue(UP) ? 1 : 0;
        int down = state.getValue(INV_DOWN) || state.getValue(DOWN) ? 1 : 0;
        int north = state.getValue(INV_NORTH) || state.getValue(NORTH) ? 1 : 0;
        int south = state.getValue(INV_SOUTH) || state.getValue(SOUTH) ? 1 : 0;
        int east = state.getValue(INV_EAST) || state.getValue(EAST) ? 1 : 0;
        int west = state.getValue(INV_WEST) || state.getValue(WEST) ? 1 : 0;

        int[] direction = { up, down, north, south, east, west };
        int i = 1;
        for (int connected : direction) {
            statusCode += connected * i;
            i*=10;
        }

        return statusCode;
    }

}
