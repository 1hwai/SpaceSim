package net.hawon.spacesim.common.block.edges.cables;

import net.hawon.spacesim.common.block.edges.EdgeBE;
import net.hawon.spacesim.common.network.Electricity;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;


public class CableBE extends EdgeBE {

    public Electricity e;

    public CableBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE.get(), pos, state);
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
    }

}
