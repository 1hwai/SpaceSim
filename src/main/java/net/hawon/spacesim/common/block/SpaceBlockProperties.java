package net.hawon.spacesim.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;

public class SpaceBlockProperties {
    //Machine|Generator attached
    public static final BooleanProperty INV_UP = BooleanProperty.create("inv_up");
    public static final BooleanProperty INV_DOWN = BooleanProperty.create("inv_down");
    public static final BooleanProperty INV_NORTH = BooleanProperty.create("inv_north");
    public static final BooleanProperty INV_SOUTH = BooleanProperty.create("inv_south");
    public static final BooleanProperty INV_EAST = BooleanProperty.create("inv_east");
    public static final BooleanProperty INV_WEST = BooleanProperty.create("inv_west");
    //Cable attached
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

}
