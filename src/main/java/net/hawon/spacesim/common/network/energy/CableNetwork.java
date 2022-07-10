package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.network.BFS;
import net.hawon.spacesim.common.network.Electricity;
import net.minecraft.world.level.Level;

import java.util.LinkedList;
import java.util.Queue;


public class CableNetwork extends BFS<SourceBE, CableBE> {
    public CableNetwork(Level level, SourceBE be) {
        super(level, be);
    }

    @Override
    public void find() {
        super.find();

    }
}