package net.hawon.spacesim.common.block.entity;

import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.energy.CapabilityEnergy;

public class CableEntity extends BlockEntity {

    protected int MAX_CURRENT;

    private int sec;

    public CableEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tickServer() {
        BlockState blockState = level.getBlockState(worldPosition);

        int powerReceived = getPower(worldPosition);
        switch (powerReceived) {
            case -1:
                level.destroyBlock(worldPosition, false);
                break;
            default:
                if (sec % 20 == 0) {
                    level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWER, powerReceived), Block.UPDATE_ALL);
                }
        }

        sec++;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }


    private int getPower(BlockPos pos) {
        BlockPos neighbors[] = { pos.above(), pos.below(), pos.north(), pos.south(), pos.east(), pos.west()};
        int power = 0;

        for (int i = 0; i < 6; i++) {
            BlockState state = level.getBlockState(neighbors[i]);
            if (state.hasProperty(BlockStateProperties.POWER)) {
                if (power < level.getBlockState(neighbors[i]).getValue(BlockStateProperties.POWER)) {
                    power = level.getBlockState(neighbors[i]).getValue(BlockStateProperties.POWER);
                }
                if (power > MAX_CURRENT) {
                    return -1;
                }
            }
        }
        return power;
    }

}
