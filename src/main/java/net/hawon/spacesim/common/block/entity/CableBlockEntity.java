package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.common.block.entity.util.PipeBlockEntity;
import net.hawon.spacesim.common.network.pipe.BFS;
import net.hawon.spacesim.common.network.pipe.StateManager;
import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends PipeBlockEntity {

    private int timer;
    private BlockPos sourcePos = null;
    private float current;
    private static float loss;

    public CableBlockEntity(BlockPos pos, BlockState state, int tier) {
        super(BlockEntityInit.CABLE.get(), pos, state);

        if (tier == 0) { //COPPER CABLE
            loss = 0.25f;
        } else {
            loss = 0.5f;
        }
        current = 0;
    }

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CABLE.get(), pos, state);
    }

    public void tickServer() {
        if (timer == 0) {
            StateManager.setState(level, worldPosition);
            BFS bfs = new BFS();
            bfs.findSource(level, worldPosition);
            bfs.setCurrent(level, sourcePos);

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
        this.current = current;
    }

    public float getCurrent() {
        return this.current;
    }

    public float getLoss() {
        return loss;
    }

}
