package net.hawon.spacesim.common.block.pipe.cables;

import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.block.pipe.PipeBE;
import net.hawon.spacesim.common.block.pipe.PipeBlock;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableBE extends PipeBE {

    private int timer;
    public SourceBE sourceBE;

    public CableBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE.get(), pos, state);
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
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
