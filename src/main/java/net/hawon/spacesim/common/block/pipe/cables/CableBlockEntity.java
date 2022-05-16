package net.hawon.spacesim.common.block.pipe.cables;

import net.hawon.spacesim.common.block.pipe.PipeBlock;
import net.hawon.spacesim.common.energy.Electricity;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.ServerCablePacket;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends PipeBlock.PipeBlockEntity {

    private int timer;
    public BlockEntity powerSource;
    public Electricity electricity;

    public CableBlockEntity(BlockPos pos, BlockState state, CableMaterial material) {
        this(pos, state);
        electricity.resistance = material.electricity.resistance;
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
    }

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE.get(), pos, state);
    }

    public void tickServer() {
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition));
        if (timer == 0) {
            StateManager.setState(level, worldPosition);
            timer++;
        }
    }

    public void setPowerSource(BlockEntity be) {
        powerSource = be;
    }

    public BlockEntity getPowerSource() {
        return powerSource;
    }

    protected BlockPos getSourcePos() {
        return powerSource.getBlockPos();
    }

}
