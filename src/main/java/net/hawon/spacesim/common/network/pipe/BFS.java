package net.hawon.spacesim.common.network.pipe;

import net.hawon.spacesim.common.block.entity.CableBlockEntity;
import net.hawon.spacesim.common.block.entity.GeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {

    public List<BlockPos> visited = new ArrayList<>();
    public Queue<BlockPos> queue = new LinkedList<>();

    public BFS() {
    }

    public void setCurrent(Level level, BlockPos sourcePos) {
        if (sourcePos != null) {
            queue.clear();
            visited.clear();

            queue.add(sourcePos);
            visited.add(sourcePos);

            while (!queue.isEmpty()) {
                BlockPos pos = queue.poll();
                BlockEntity be = level.getBlockEntity(pos);

                for (Direction direction : Direction.values()) {
                    BlockPos rel = pos.relative(direction);

                    if (!visited.contains(rel)) {
                        BlockEntity relBE = level.getBlockEntity(rel);
                        if (relBE instanceof CableBlockEntity relCableBE) {

                            if (be instanceof CableBlockEntity cableBE) {
                                float test = cableBE.getCurrent() - cableBE.getLoss();
                                if (test >= 0) {
                                    relCableBE.setCurrent(test);
                                } else {
                                    relCableBE.setCurrent(0);
                                }
                            } else if (be instanceof GeneratorBlockEntity ge) {
                                relCableBE.setCurrent(ge.getOutputPerTick());
                            }
                            queue.add(rel);
                            visited.add(rel);
                        }

                    }
                }
            }
        }
    }

    public void findSource(Level level, BlockPos cablePos) {
        queue.clear();
        visited.clear();

        queue.add(cablePos);
        visited.add(cablePos);

        int maxTier = 0;
        boolean geConnected = false;

        CableBlockEntity cableBE = (CableBlockEntity) level.getBlockEntity(cablePos);

        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();

            for (Direction direction : Direction.values()) {
                BlockPos rel = pos.relative(direction);

                if (!visited.contains(rel)) {
                    BlockEntity be = level.getBlockEntity(rel);

                    if (be != null) {
                        if (be instanceof CableBlockEntity) {
                            queue.add(rel);
                            visited.add(rel);
                        } else if (be instanceof GeneratorBlockEntity ge) {
                            if (ge.getTier() > maxTier) {
                                maxTier = ge.getTier();
                                cableBE.setSourcePos(rel);
                            }
                            geConnected = true;
                        }
                    }
                }
            }
        }

        if (!geConnected) {
            cableBE.setSourcePos(null);
            cableBE.setCurrent(0);
        }

    }

}
