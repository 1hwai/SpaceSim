package net.hawon.spacesim.client;

import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("resource")
public class ClientAccess {
    public static boolean updateCable(BlockPos pos) {
        final BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
        if (blockEntity instanceof CableBE cableBE) {
//            cableBE.setChanged();
            return true;
        }
        return false;
    }
}