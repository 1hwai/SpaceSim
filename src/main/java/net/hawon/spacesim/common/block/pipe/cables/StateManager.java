package net.hawon.spacesim.common.block.pipe.cables;

import net.hawon.spacesim.common.block.util.SpaceBlockProperties;
import net.hawon.spacesim.common.block.generator.GeneratorBlockEntity;
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

            if (be instanceof GeneratorBlockEntity) {
                attachment[0] = true;
            } else if (be instanceof CableBlockEntity) {
                attachment[1] = true;
            }
            blockState = blockState.setValue(attach(direction, true), attachment[0]);
            blockState = blockState.setValue(attach(direction, false), attachment[1]);

        }

        level.setBlock(pos, blockState, Block.UPDATE_ALL);
    }

    public static BooleanProperty attach(Direction direction, Boolean inv) {
        if (inv) { //inv = true;
            switch (direction) {
                case UP: return SpaceBlockProperties.INV_UP;
                case DOWN: return SpaceBlockProperties.INV_DOWN;
                case NORTH: return SpaceBlockProperties.INV_NORTH;
                case SOUTH: return SpaceBlockProperties.INV_SOUTH;
                case EAST: return SpaceBlockProperties.INV_EAST;
                case WEST: return SpaceBlockProperties.INV_WEST;
            }
        } else {
            switch (direction) {
                case UP: return SpaceBlockProperties.UP;
                case DOWN: return SpaceBlockProperties.DOWN;
                case NORTH: return SpaceBlockProperties.NORTH;
                case SOUTH: return SpaceBlockProperties.SOUTH;
                case EAST: return SpaceBlockProperties.EAST;
                case WEST: return SpaceBlockProperties.WEST;
            }
        }
        return null;
    }

}
