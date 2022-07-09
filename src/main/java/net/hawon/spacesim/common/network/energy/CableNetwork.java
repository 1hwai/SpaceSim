package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.network.BFS;
import net.hawon.spacesim.common.network.Electricity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.LinkedList;
import java.util.Queue;


public class CableNetwork  {

    private final Queue<Electricity> electricityQueue = new LinkedList<>();

//    public CableNetwork(Level level, CableBE cableBE) {
//        super(level, cableBE);
//    }

//    @Override
//    public void find() {
//        /*
//        * BFS algorithm has been used
//        * */
//        super.find();
//        electricityQueue.clear();
//        electricityQueue.add(new Electricity());
//
//        boolean hasFound = false;
//
//        while (!queue.isEmpty()) {
//            CableBE cableBE = queue.poll();
//            Electricity electricity = electricityQueue.poll();
//            for (Direction direction : Direction.values()) {
//                BlockEntity neighborBE = level.getBlockEntity(cableBE.getBlockPos().relative(direction));
//                if (!visited.contains(neighborBE)) {
//                    if (neighborBE instanceof CableBE neighborCableBE) {
//                        queue.add(neighborCableBE);
//                        visited.add(neighborCableBE);
//                    } else if (neighborBE instanceof SourceBE sourceBE) {
//                        be.setSourceBE(sourceBE);
//                        hasFound = true;
//                        break;
//                    }
//                }
//            }
//        }
//
//        if (!hasFound)
//            be.setSourceBE(null);
//
//    }
}