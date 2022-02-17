package net.hawon.spacesim.api.block.state;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SpaceBlockStateProperties {
    public static final IntegerProperty MACHINE_CONNECTED = IntegerProperty.create("machine_connected", 0, 1);
}
