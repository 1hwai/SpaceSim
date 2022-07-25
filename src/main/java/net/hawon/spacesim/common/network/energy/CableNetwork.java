package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.machines.MachineBE;
import net.hawon.spacesim.common.block.machines.skeleton.SourceBE;
import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.network.BFS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class CableNetwork extends BFS<CableBE, CableBE> {
    public CableNetwork(Level level, CableBE be) {
        super(level, be);
    }

    @Override
    public void find() {
        clear();

        SourceBE sourceBE = null;
        //Fix required. Should make source lists and pick one of them
        Direction cableInput = null;
        Element<CableBE> start = new Element<>(null, be);

        queue.add(start);
        visited.add(be);

        while (!queue.isEmpty() && sourceBE == null) {
            Element<CableBE> element = queue.poll();
            CableBE cableBE = element.edge;
            for (Direction direction : Direction.values()) {
                BlockEntity neighborBE = level.getBlockEntity(cableBE.getBlockPos().relative(direction));
                if (neighborBE instanceof SourceBE _sourceBE) {
                    if (direction == _sourceBE.output.getOpposite()) {
                        sourceBE = _sourceBE;
                        cableInput = cableBE == be ? _sourceBE.output : Direction.fromNormal(traceBack(element).subtract(be.getBlockPos()));
                    }
                } else if (neighborBE instanceof CableBE neighborCableBE) {
                    queue.add(new Element<>(element, neighborCableBE));
                    visited.add(cableBE);
                }
            }
        }

        if (sourceBE == null) return;

        clear();

        for (Direction direction : Direction.values()) {
            if (direction != Objects.requireNonNull(cableInput).getOpposite()) {
                BlockEntity neighborBE = level.getBlockEntity(be.getBlockPos().relative(direction));
                if (neighborBE instanceof CableBE neighborCableBE) {
                    queue.add(new Element<>(start, neighborCableBE));
                }
            }
        }
        while (!queue.isEmpty()) {
            Element<CableBE> element = queue.poll();
            CableBE cableBE = element.edge;
            for (Direction direction : Direction.values()) {
                BlockEntity neighborBE = level.getBlockEntity(cableBE.getBlockPos().relative(direction));
                if (neighborBE instanceof MachineBE machineBE) {
                    if (direction == machineBE.input.getOpposite()) {
                        machineBE.setParent(sourceBE);
                    }
                }
            }
        }


    }

    private BlockPos traceBack(Element<CableBE> element) {
        Element<CableBE> temp = element;
        while (temp.from.from != null) {
            temp = temp.from;
        }
        return temp.edge.getBlockPos();
    }
}