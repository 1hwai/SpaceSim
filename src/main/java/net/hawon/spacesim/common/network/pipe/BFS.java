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

    public void setDistance(Level level, BlockPos sourcePos) {

    }

    public void setSource(Level level, BlockPos cablePos) {
        queue.clear();
        visited.clear();

        queue.add(cablePos);
        visited.add(cablePos);



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
                        } else if (be instanceof GeneratorBlockEntity) {
                            CableBlockEntity cableBE = (CableBlockEntity) level.getBlockEntity(cablePos);
                            cableBE.sourcePos = rel;
                        }
                    }
                }
            }
        }
    }


}
