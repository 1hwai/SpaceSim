package net.hawon.spacesim.client;

import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("resource")
public class ClientAccess {
    public static boolean updateCable(BlockPos pos) {
        final BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
        if (blockEntity instanceof CableBlockEntity cableBE) {
            cableBE.setChanged();
            return true;
        }
        return false;
    }
}