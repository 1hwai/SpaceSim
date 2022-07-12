package net.hawon.spacesim.common.network;

import net.hawon.spacesim.common.block.edges.EdgeBE;
import net.hawon.spacesim.common.block.machines.skeleton.NodeBE;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.*;

public abstract class BFS<T extends BlockEntity, E extends EdgeBE> {

    public final Level level;
    public final T be;

    public final Queue<Element<E>> queue = new LinkedList<>();
    public final ArrayList<E> visited = new ArrayList<>();

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
    }

    protected static class Element<E extends EdgeBE> {
        public Element<E> from;
        public E edge;

        public Element(Element<E> from, E node) {
            this.from = from;
            this.edge = node;
        }
    }

}
