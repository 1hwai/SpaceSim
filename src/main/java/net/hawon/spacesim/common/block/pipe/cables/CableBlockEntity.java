package net.hawon.spacesim.common.block.pipe.cables;

import net.hawon.spacesim.common.block.machines.SourceBlockEntity;
import net.hawon.spacesim.common.block.pipe.PipeBlock;
import net.hawon.spacesim.common.energy.Electricity;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends PipeBlock.PipeBlockEntity {

    private int timer;
    public SourceBlockEntity source;
    public Electricity e;

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE.get(), pos, state);
    }

    public CableBlockEntity(BlockPos pos, BlockState state, CableMaterial material) {
        this(pos, state);
        e.resistance = material.e.resistance;
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
        StateManager.setState(level, worldPosition);
    }

    public void tick() {
//        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
    }

    public void setSource(SourceBlockEntity source) {
        this.source = source;
    }

    public SourceBlockEntity getSource() {
        return source;
    }

}
