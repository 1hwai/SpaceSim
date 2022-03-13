package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.network.pipe.BFS;
import net.hawon.spacesim.common.network.pipe.StateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CableBlockEntity extends PipeBlockEntity {

    private int timer;
    public BlockPos sourcePos = null;
    public int distance;

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

}
