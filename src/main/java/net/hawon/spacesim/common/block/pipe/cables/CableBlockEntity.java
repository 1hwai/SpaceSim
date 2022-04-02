package net.hawon.spacesim.common.block.pipe.cables;

import net.hawon.spacesim.common.block.pipe.PipeBlock;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.ServerEnergyPacket;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends PipeBlock.PipeBlockEntity {

    private int timer;
    private BlockPos sourcePos = null;
    private float current;
    private static float loss;

    public CableBlockEntity(BlockPos pos, BlockState state, int tier) {
        this(pos, state);
        PacketHandler.INSTANCE.sendToServer(new ServerEnergyPacket(this.worldPosition));

        if (tier == 0) { //COPPER CABLE
            loss = 0.25f;
        } else {
            loss = 0.5f;
        }
    }

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE.get(), pos, state);
    }

    public void tickServer() {
        if (timer == 0) {
            StateManager.setState(level, worldPosition);
            timer++;
        }
    }

    public void setSourcePos(BlockPos pos) {
        this.sourcePos = pos;
    }

    public BlockPos getSourcePos() {
        return sourcePos;
    }

    public void setCurrent(float current) {
        if (current < 0)
            current = 0;
        this.current = current;
    }

    public float getCurrent() {
        return current;
    }

    public float getLoss() {
        return loss;
    }

}
