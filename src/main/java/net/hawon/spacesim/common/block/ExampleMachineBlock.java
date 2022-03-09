package net.hawon.spacesim.common.block;

import net.hawon.spacesim.common.block.entity.CopperCableBlockEntity;
import net.hawon.spacesim.common.block.entity.ExampleMachineBlockEntity;
import net.hawon.spacesim.common.block.entity.GeneratorBlockEntity;
import net.hawon.spacesim.common.energy.CustomEnergyStorage;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExampleMachineBlock extends Block implements EntityBlock {

    public ExampleMachineBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ExampleMachineBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState state, BlockEntityType<T> beType) {
        if (!level.isClientSide()) {
            return (level0, pos, state0, blockEntity) -> {
                if (blockEntity instanceof ExampleMachineBlockEntity be) {
                    be.tickServer();
                }
            };
        }

        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ExampleMachineBlockEntity machineBlock) {
                Item item = player.getItemInHand(hand).getItem();
                if (item.asItem() == ItemInit.GALVANOMETER.get()) {
                    String energyStored = machineBlock.getCapability(CapabilityEnergy.ENERGY).map(handler -> handler.getEnergyStored()).get().toString();
                    player.sendMessage(new TextComponent(energyStored), player.getUUID());
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
