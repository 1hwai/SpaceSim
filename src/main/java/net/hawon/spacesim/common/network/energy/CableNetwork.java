package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.nodes.skeleton.NodeBE;
import net.hawon.spacesim.common.block.nodes.skeleton.SourceBE;
import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.network.BFS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;

public class CableNetwork extends BFS<CableBE, CableBE> {

    BlockPos was;

    SourceBE sourceBE = null;
    ArrayList<NodeBE> children = new ArrayList<>();
    //Fix required. Should make source lists and pick one of them

    Direction flow = null;
    Element<CableBE> start;

    public CableNetwork(Level level, CableBE be) {
        super(level, be);
    }

    /*
    * CableBlock.destroy() will call this method
    * */
    public CableNetwork(Level level, BlockPos pos) {
        super(level, null);
        was = pos;
    }

    @Override
    public void find() {
        start = new Element<>(null, be);
        findSourceBE();
        findChildren();
    }

    public void killConnection() {
        for (Direction direction : Direction.values()) {
            BlockEntity neighborBE = level.getBlockEntity(was.relative(direction));
            if (neighborBE instanceof SourceBE sourceBE) {
                if (direction == sourceBE.output.getOpposite()) {
                    sourceBE.rmChildren();
                }
            } else if (neighborBE instanceof CableBE neighborCableBE) {
                CableNetwork network = new CableNetwork(level, neighborCableBE);
                network.find();
            }
        }
    }

    private void findSourceBE() {
        clear();

        queue.add(start);
        visited.add(be);

        while (!queue.isEmpty() && sourceBE == null) {
            Element<CableBE> element = queue.poll();
            CableBE cableBE = element.edge;
            for (Direction direction : Direction.values()) {
                BlockEntity neighborBE;
                neighborBE = level.getBlockEntity(cableBE.getBlockPos().relative(direction));
                if (neighborBE instanceof SourceBE _sourceBE) {
                    if (direction == _sourceBE.output.getOpposite()) {
                        sourceBE = _sourceBE;
                        flow = cableBE == be ? _sourceBE.output : Direction.fromNormal(be.getBlockPos().subtract(traceBack(element)));
                    }
                } else if (neighborBE instanceof CableBE neighborCableBE) {
                    if (!visited.contains(neighborCableBE)) {
                        queue.add(new Element<>(element, neighborCableBE));
                        visited.add(cableBE);
                    }
                }
            }
        }

        if (sourceBE != null) {
            System.out.println("Me : " + be + " " + be.getBlockPos());
            System.out.println("SourceBE : " + sourceBE + " " + sourceBE.getBlockPos());
        }
    }

    private void findChildren() {
        clear();

        for (Direction direction : Direction.values()) {
            if (flow == null || direction != flow.getOpposite()) {
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
                if (neighborBE instanceof NodeBE nodeBE) {
                    if (direction == nodeBE.input.getOpposite()) {
                        if (sourceBE != null) {
                            System.out.println("New Kid : " + nodeBE);
                            nodeBE.setParent(sourceBE);
                            children.add(nodeBE);
                        }
                    }
                } else if (neighborBE instanceof CableBE neighborCableBE) {
                    if (!visited.contains(neighborCableBE)) {
                        queue.add(new Element<>(element, neighborCableBE));
                        visited.add(cableBE);
                    }
                }
            }
        }

        if (sourceBE != null)
            sourceBE.validateChildren(children);
        if (sourceBE == null && !children.isEmpty()) {
            children.forEach(NodeBE::rmParent);
        }
    }

    private BlockPos traceBack(Element<CableBE> element) {
        Element<CableBE> temp = element;
        while (temp.from != null && temp.from.from != null) {
            temp = temp.from;
        }
        return temp.edge.getBlockPos();
    }
    /*
    * [x,o]
    *    ^
    *   [o,o]
    *      ^
    *     [o,o]
    *        ^
    *       [o,o]
    * */
}