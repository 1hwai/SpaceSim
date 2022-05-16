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
    private final CableBlockEntity cableBE;

    public CableEnergyNetwork(Level level, CableBlockEntity cableBE) {
        this.level = level;
        this.cableBE = cableBE;
    }

    public void setCurrent() {
        BlockPos sourcePos = cableBE.getPowerSource().getBlockPos();
        if (sourcePos == null)
            return;

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


    public void findSource() {
        BlockPos cablePos = cableBE.getBlockPos();
        queue.clear();
        visited.clear();

        queue.add(cablePos);
        visited.add(cablePos);

        boolean powerConnected = false;

        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();

            for (Direction direction : Direction.values()) {
                BlockPos rel = pos.relative(direction);

                if (!visited.contains(rel)) {
                    BlockEntity be = level.getBlockEntity(rel);

                    if (be instanceof CableBlockEntity) {
                        queue.add(rel);
                        visited.add(rel);
                    } else if (be instanceof GeneratorBlockEntity ge) {
                        if (ge.getTier() > maxTier) {
                            maxTier = ge.getTier();
                            cableBE.setSourcePos(rel);
                        }
                        cableBE.setPowerSource();
                        powerConnected = true;
                    }
                }
            }
        }

        if (!powerConnected) {
            cableBE.setPowerSource(null);
        }

    }

}
