package net.hawon.spacesim.common.block.entity;

import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class CableEntity extends BlockEntity {

    private final CustomEnergyStorage energyStorage = createEnergy();
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    public CableEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tickServer() {
//        for (Direction direction : Direction.values()) {
//
//            if (level.getBlockState(worldPosition.relative(direction)).getBlock() == BlockInit.GENERATOR.get()) {
//                //level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(SpaceBlockStateProperties), );
//            }
//        }
    }




    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(10, 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }


}
