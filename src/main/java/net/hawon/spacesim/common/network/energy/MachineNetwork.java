package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.block.machines.MachineBE;
import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.network.BFS;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;


public class MachineNetwork extends BFS<MachineBE, CableBE> {

    public MachineNetwork(Level level, MachineBE be) {
        super(level, be);
    }

    @Override
    public void find() {
        super.find();

        // To get the starting of the edge
        if (be.getCableDirection() == null) return;
        BlockEntity input = level.getBlockEntity(be.getBlockPos().relative(be.getCableDirection()));
        if (input instanceof CableBE inputCable) {
            queue.add(new Element<>(null, inputCable));
            visited.add(inputCable);
        }

        boolean hasFound = false;

        while (!queue.isEmpty()) {
            Element<CableBE> element = queue.poll();
            CableBE cableBE = element.edge;
            for (Direction direction : Direction.values()) {
                BlockEntity neighborBE = level.getBlockEntity(cableBE.getBlockPos().relative(direction));
                if (neighborBE instanceof CableBE neighborCableBE) {
                    if (!visited.contains(neighborCableBE)) {
                        queue.add(new Element<>(element, neighborCableBE));
                        visited.add(neighborCableBE);
                    }
                } else if (neighborBE instanceof SourceBE sourceBE) {
                    be.setParent(sourceBE);
                    hasFound = true;
                    element.edge.e = sourceBE.e;
                    traceBack(element);
                    break;
                }
            }
        }

        if (!hasFound) be.rmParent();

    }

    private void traceBack(final Element<CableBE> element) {
        Element<CableBE> temp = element;
        while (temp.from != null) {
            temp.from.edge.e.voltageDrop(temp.edge.e);
            temp = temp.from;
        }
    }

}
