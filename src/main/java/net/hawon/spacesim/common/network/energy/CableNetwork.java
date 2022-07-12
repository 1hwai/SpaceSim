package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.network.BFS;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public class CableNetwork extends BFS<CableBE, CableBE> {
    public CableNetwork(Level level, CableBE be) {
        super(level, be);
    }

    @Override
    public void find() {
        super.find();

        queue.add(new Element<>(null, be));
        visited.add(be);

        while (!queue.isEmpty()) {
            Element<CableBE> element = queue.poll();
            CableBE cableBE = element.edge;
            for (Direction direction : Direction.values()) {

            }
        }


    }
}