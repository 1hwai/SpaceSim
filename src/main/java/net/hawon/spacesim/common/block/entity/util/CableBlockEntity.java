package net.hawon.spacesim.common.block.entity.util;

import net.hawon.spacesim.common.block.entity.util.PipeBlockEntity;
import net.hawon.spacesim.common.network.pipe.BFS;
import net.hawon.spacesim.common.network.pipe.StateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CableBlockEntity extends PipeBlockEntity {

    private int timer;
    private BlockPos sourcePos = null;
    private int distance;

    public CableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tickServer() {
        if (timer == 0) {
            StateManager.setState(level, worldPosition);
            BFS bfs = new BFS();
            bfs.findSource(level, worldPosition);
            bfs.setDistance(level, sourcePos);

            timer++;
        }
    }

    public void setSourcePos(BlockPos pos) {
        this.sourcePos = pos;
    }

    public BlockPos getSourcePos() {
        return sourcePos;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

}
