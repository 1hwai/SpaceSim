package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.block.nodes.MachineBE;
import net.hawon.spacesim.common.network.BFS;
import net.minecraft.world.level.Level;

public class MachineNetwork extends BFS<MachineBE, CableBE> {

    public MachineNetwork(Level level, MachineBE be) {
        super(level, be);
    }

    @Override
    public void find() {

    }

//    @Override
//    public void find() {
//
//        // To get the starting of the edge
//        if (be.getCableDirection() == null) return;
//        BlockEntity input = level.getBlockEntity(be.getBlockPos().relative(be.getCableDirection()));
//        if (input instanceof CableBE inputCable) {
//            queue.add(new Element<>(null, inputCable));
//            visited.add(inputCable);
//        }
//
//        boolean hasFound = false;
//
//        while (!queue.isEmpty()) {
//            Element<CableBE> element = queue.poll();
//            CableBE cableBE = element.edge;
//            for (Direction direction : Direction.values()) {
//                BlockEntity neighborBE = level.getBlockEntity(cableBE.getBlockPos().relative(direction));
//                if (neighborBE instanceof CableBE neighborCableBE) {
//                    if (!visited.contains(neighborCableBE)) {
//                        queue.add(new Element<>(element, neighborCableBE));
//                        visited.add(neighborCableBE);
//                    }
//                } else if (neighborBE instanceof SourceBE sourceBE) {
//                    be.setParent(sourceBE);
//                    hasFound = true;
//                    element.edge.e = sourceBE.e;
//                    break;
//                }
//            }
//        }
//
//        if (!hasFound) be.rmParent();
//
//    }

//    private void traceBack(final Element<CableBE> element) {
//        Element<CableBE> temp = element;
//        while (temp.from != null) {
//            temp.from.edge.e.voltageDrop(temp.edge.e);
//            temp = temp.from;
//        }
//    }

}
