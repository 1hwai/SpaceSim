package net.hawon.spacesim.common.block.entity;

import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.energy.CapabilityEnergy;

public class CableEntity extends BlockEntity {

    protected int MAX_CURRENT;

    public CableEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tickServer() {
        BlockState blockState = level.getBlockState(worldPosition);

        int powerReceived = getPower();
        switch (powerReceived) {
            case -1:
                level.destroyBlock(worldPosition, false);
                break;
            default:
                level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWER, powerReceived), Block.UPDATE_ALL);
        }

    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }


    private int getPower() {
        int power = 0;
        for (Direction direction : Direction.values()) {
            BlockEntity be = level.getBlockEntity(worldPosition.relative(direction));
            if (be != null) {

            }
        }
//        for (int i = 0; i < 6; i++) {
//            BlockState state = level.getBlockState(neighbors[i]);
//            if (state.hasProperty(BlockStateProperties.POWER)) {
//                if (power < state.getValue(BlockStateProperties.POWER)) {
//                    power = state.getValue(BlockStateProperties.POWER);
//                }
//                if (power > MAX_CURRENT) {
//                    return -1;
//                }
//            }
//        }
        return power;
    }

}
