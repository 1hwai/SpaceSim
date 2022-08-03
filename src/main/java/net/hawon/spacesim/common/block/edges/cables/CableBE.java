package net.hawon.spacesim.common.block.edges.cables;

import net.hawon.spacesim.common.block.edges.EdgeBE;
import net.hawon.spacesim.common.network.Electricity;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class CableBE extends EdgeBE {

    public Electricity e;

    public CableBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE.get(), pos, state);
        find();
        e = new Electricity();
    }

    public void find() {
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
    }

    public BlockEntity getRelative(@NotNull Level level, Direction direction) {
        return level.getBlockEntity(worldPosition.relative(direction));
    }

}
