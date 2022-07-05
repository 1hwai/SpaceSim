package net.hawon.spacesim.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class BFS<T extends BlockEntity> {

    public final Level level;
    public final T be;

    public final Queue<T> queue = new LinkedList<>();
    public final ArrayList<T> visited = new ArrayList<>();

    public BFS(Level level, T be) {
        this.level = level;
        this.be = be;
    }

    /*
    * find()
    * Call super.find()
    * Override this method when use
    * */
    public void find() {
        queue.clear();
        visited.clear();
        queue.add(be);
        visited.add(be);
    }
}
