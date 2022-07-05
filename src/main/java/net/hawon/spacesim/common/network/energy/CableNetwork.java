package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.block.pipe.cables.CableBE;
import net.hawon.spacesim.common.network.BFS;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CableNetwork extends BFS<CableBE> {

    public CableNetwork(Level level, CableBE cableBE) {
        super(level, cableBE);
    }

    @Override
    public void find() {
        /*
        * BFS algorithm has been used
        * */
        super.find();

        boolean hasFound = false;

        while (!queue.isEmpty()) {
            CableBE cableBE = queue.poll();
            for (Direction direction : Direction.values()) {
                BlockEntity neighborBE = level.getBlockEntity(cableBE.getBlockPos().relative(direction));
                if (!visited.contains(neighborBE)) {
                    if (neighborBE instanceof CableBE neighborCableBE) {
                        queue.add(neighborCableBE);
                        visited.add(neighborCableBE);
                    } else if (neighborBE instanceof SourceBE sourceBE) {
                        be.setSourceBE(sourceBE);
                        hasFound = true;
                        break;
                    }
                }
            }
        }

        if (!hasFound)
            be.setSourceBE(null);

    }
}