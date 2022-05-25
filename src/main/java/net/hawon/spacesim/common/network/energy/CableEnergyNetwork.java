package net.hawon.spacesim.common.network.energy;

import net.hawon.spacesim.common.block.machines.SourceBlockEntity;
import net.hawon.spacesim.common.block.machines.generator.GeneratorOM.GeneratorOMBlockEntity;
import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.hawon.spacesim.common.block.generator.GeneratorBlockEntity;
import net.hawon.spacesim.common.energy.Electricity;
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

    private final Level level;
    private final CableBlockEntity cableBE;

    private final List<BlockPos> visited = new ArrayList<>();
    private final Queue<BlockEntity> queue = new LinkedList<>();

    public CableEnergyNetwork(Level level, CableBlockEntity cableBE) {
        this.level = level;
        this.cableBE = cableBE;
    }

//    public void setElectricity() {
//        BlockPos sourcePos = cableBE.getSource().getBlockPos();
//        if (sourcePos == null)
//            return;
//
//        queue.clear();
//        visited.clear();
//
//        queue.add(sourcePos);
//        visited.add(sourcePos);
//
//        while (!queue.isEmpty()) {
//            BlockPos pos = queue.poll();
//            BlockEntity be = level.getBlockEntity(pos);
//
//            for (Direction direction : Direction.values()) {
//                BlockPos rel = pos.relative(direction);
//
//                if (!visited.contains(rel)) {
//                    BlockEntity relBE = level.getBlockEntity(rel);
//                    if (relBE instanceof CableBlockEntity relCableBE) {
//
//                        if (be instanceof CableBlockEntity cableBE) {
//                            float test = cableBE.e() - cableBE.getLoss();
//                            if (test >= 0) {
//                                relCableBE.setCurrent(test);
//                            } else {
//                                relCableBE.setCurrent(0);
//                            }
//                        } else if (be instanceof GeneratorBlockEntity) {
//                            relCableBE.setCurrent(GeneratorBlockEntity.getMaxExtract());
//                        }
//                        queue.add(rel);
//                        visited.add(rel);
//                    }
//
//                }
//            }
//        }
//    }

    public void setElectricity() {
        SourceBlockEntity sourceBE = cableBE.getSource();

        queue.clear();
        visited.clear();

        queue.add(sourceBE);
        visited.add(sourceBE.getBlockPos());

        while (!queue.isEmpty()) {
            BlockEntity be = queue.poll();
            for (Direction direction : Direction.values()) {
                BlockEntity relBE = level.getBlockEntity(be.getBlockPos().relative(direction));
                if (!visited.contains(relBE.getBlockPos())) {
                    if (relBE instanceof CableBlockEntity relCableBE) {
                        if (be instanceof CableBlockEntity cableBE) {
                            Electricity test = cableBE.e;
                            test.setVoltage(test.voltage - test.current * test.resistance);
                            if (test.isVoltageValid()) {
                                relCableBE.e.setCurrent(test.current);
                                relCableBE.e.setVoltage(test.voltage);
                            }
                        } else if (be instanceof GeneratorOMBlockEntity gom) {
                            //여기서부터 수정해야.
                            //CableEnergyNetwork 수정하고 커밋할 것.
                            //PacketHandler 관련 이슈 해결 후 테스트할 것.
                        }
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
