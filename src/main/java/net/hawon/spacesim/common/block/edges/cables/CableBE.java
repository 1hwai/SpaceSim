package net.hawon.spacesim.common.block.edges.cables;

import net.hawon.spacesim.common.block.nodes.skeleton.SourceBE;
import net.hawon.spacesim.common.block.edges.EdgeBE;
import net.hawon.spacesim.common.network.Electricity;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class CableBE extends EdgeBE {

    private int timer;
    public SourceBE sourceBE;
    public Electricity e;

    public CableBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE.get(), pos, state);
        e = new Electricity(0, 0, 0.15);
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
    }

    public void find() {
        if (!Objects.requireNonNull(level).isClientSide()) {
            System.out.println(worldPosition);
            PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
        }
    }

    public void tick() {
        timer++;
    }

    public void setSourceBE(SourceBE sourceBE) {
        this.sourceBE = sourceBE;
    }

    public SourceBE getSourceBE() {
        return sourceBE;
    }

}
