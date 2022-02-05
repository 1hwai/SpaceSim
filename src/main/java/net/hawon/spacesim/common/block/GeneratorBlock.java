package net.hawon.spacesim.common.block;

import net.hawon.spacesim.common.block.entity.ExampleChestBlockEntity;
import net.hawon.spacesim.common.block.entity.GeneratorBlockEntity;
import net.hawon.spacesim.common.container.ExampleChestContainer;
import net.hawon.spacesim.common.container.GeneratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class GeneratorBlock extends Block implements EntityBlock {

    public static final String GEN_SCREEN = "Generator";

    public GeneratorBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState state, BlockEntityType<T> beType) {
        if (!level.isClientSide()) {
            return (level0, pos, state0, blockEntity) -> {
                if (blockEntity instanceof GeneratorBlockEntity be) {
                    be.tickServer();
                }
            };
        }

        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GeneratorBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof final GeneratorBlockEntity generator) {
                final MenuProvider container = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent(GEN_SCREEN);
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player1) {
                        return new GeneratorContainer(id, pos, inv, player1);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, container, pos);

            } else {
                throw new IllegalStateException("Error: Container Missing");
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(BlockStateProperties.POWERED);
    }
}
