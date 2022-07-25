package net.hawon.spacesim.common.block.edges;

import net.hawon.spacesim.common.block.machines.skeleton.NodeBE;
import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.block.utils.SpaceBlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

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
                case UP -> SpaceBlockProperties.INV_UP;
                case DOWN -> SpaceBlockProperties.INV_DOWN;
                case NORTH -> SpaceBlockProperties.INV_NORTH;
                case SOUTH -> SpaceBlockProperties.INV_SOUTH;
                case EAST -> SpaceBlockProperties.INV_EAST;
                case WEST -> SpaceBlockProperties.INV_WEST;
            };
        } else {
            return switch (direction) {
                case UP -> SpaceBlockProperties.UP;
                case DOWN -> SpaceBlockProperties.DOWN;
                case NORTH -> SpaceBlockProperties.NORTH;
                case SOUTH -> SpaceBlockProperties.SOUTH;
                case EAST -> SpaceBlockProperties.EAST;
                case WEST -> SpaceBlockProperties.WEST;
            };
        }
    }

}
