package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.hawon.spacesim.common.block.generator.GeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CableEnergyNetwork {

    //Using bfs algorithm

    private final List<BlockPos> visited = new ArrayList<>();
    private final Queue<BlockPos> queue = new LinkedList<>();

    private final Level level;

    public CableEnergyNetwork(Level level) {
        this.level = level;
    }

    public void update(BlockPos cablePos, BlockPos sourcePos) {
        findSource(cablePos);
        setCurrent(sourcePos);
    }

    public void setCurrent(BlockPos sourcePos) {
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
                            } else if (be instanceof GeneratorBlockEntity) {
                                relCableBE.setCurrent(GeneratorBlockEntity.getMaxExtract());
                            }
                            queue.add(rel);
                            visited.add(rel);
                        }

                    }
                }
            }
        }
    }

    public void findSource(BlockPos cablePos) {
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
