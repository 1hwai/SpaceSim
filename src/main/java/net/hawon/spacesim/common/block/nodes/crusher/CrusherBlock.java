package net.hawon.spacesim.common.block.nodes.crusher;

import net.hawon.spacesim.common.block.nodes.skeleton.NodeBlock;
import net.hawon.spacesim.common.container.CrusherContainer;
import net.hawon.spacesim.common.item.RenchItem;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class CrusherBlock extends NodeBlock implements EntityBlock {

    public static final VoxelShape NORTH = makeShape(Direction.NORTH);
    public static final VoxelShape SOUTH = makeShape(Direction.SOUTH);
    public static final VoxelShape WEST = makeShape(Direction.WEST);
    public static final VoxelShape EAST = makeShape(Direction.EAST);

    public CrusherBlock(Properties properties) {
        super(properties);

        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
            default -> NORTH;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState state, BlockEntityType<T> beType) {
        if (!level.isClientSide()) {
            return (level0, pos, state0, blockEntity) -> {
                if (blockEntity instanceof CrusherBE be) {
                    be.tick();
                }
            };
        }

        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrusherBE(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof CrusherBE crusherBE) {
//                PacketHandler.INSTANCE.sendToServer(new ServerMachinePacket(pos));
            }
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, @NotNull Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof CrusherBE crusherBE) {
                Item item = player.getItemInHand(hand).getItem();
                if (item == ItemInit.RENCH.get())
                    return RenchItem.rotate(state, level, pos, player);
                if (item == ItemInit.GALVANOMETER.get()) {
                    System.out.println(crusherBE.parent + " input: " + crusherBE.input);
                    return InteractionResult.FAIL;
                }

                final MenuProvider container = new MenuProvider() {
                    @Override
                    public @NotNull Component getDisplayName() {
                        return CrusherBE.TITLE;
                    }

                    @Override
                    public @NotNull AbstractContainerMenu createMenu(int id, Inventory inv, Player player1) {
                        return new CrusherContainer(id, pos, inv, player1);
                    }
                };

                NetworkHooks.openGui((ServerPlayer) player, container, pos);

            } else {
                throw new IllegalStateException("Error: Crusher Container Missing");
            }
        }

        return InteractionResult.SUCCESS;
    }

    public static VoxelShape makeShape(Direction direction){
        VoxelShape shape = Shapes.empty();
        switch (direction) {
            case SOUTH -> {
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.3125, 0.9375, 0.8125, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.5, 0.9375, 0.8125, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.6875, 0.9375, 0.8125, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.875, 0.9375, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.125, 0.9375, 0.8125, 0.25), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.1875, 0.375, 0.8125, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.375, 0.375, 0.8125, 0.5), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.5625, 0.375, 0.8125, 0.6875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.75, 0.375, 0.8125, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.0625, 0.375, 0.8125, 0.125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.75, 0.0625, 0.0625, 1, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.9375, 0.75, 0.0625, 1, 1, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 0.0625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.75, 0.9375, 1, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.8125, 0.0625, 0.5625, 0.875, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.75, 1), BooleanOp.OR);
            }
            case EAST -> {
                shape = Shapes.join(shape, Shapes.box(0.3125, 0.75, 0.0625, 0.4375, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5, 0.75, 0.0625, 0.625, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.6875, 0.75, 0.0625, 0.8125, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.875, 0.75, 0.0625, 0.9375, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0.75, 0.0625, 0.25, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.75, 0.625, 0.3125, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.625, 0.5, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.75, 0.625, 0.6875, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0.75, 0.625, 0.875, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.625, 0.125, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.9375, 0.9375, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0, 0.9375, 1, 0.0625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 0.0625, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.9375, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.8125, 0.4375, 0.9375, 0.875, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.75, 1), BooleanOp.OR);
            }
            case WEST -> {
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.75, 0.625, 0.6875, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.625, 0.5, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.75, 0.625, 0.3125, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.625, 0.125, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0.75, 0.625, 0.875, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.6875, 0.75, 0.0625, 0.8125, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5, 0.75, 0.0625, 0.625, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.3125, 0.75, 0.0625, 0.4375, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0.75, 0.0625, 0.25, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.875, 0.75, 0.0625, 0.9375, 0.8125, 0.375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0, 0.9375, 1, 0.0625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.9375, 0.9375, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.9375, 0.75, 0, 1, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 0.0625, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.8125, 0.4375, 0.9375, 0.875, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.75, 1), BooleanOp.OR);
            }
            default -> {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.5625, 0.375, 0.8125, 0.6875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.375, 0.375, 0.8125, 0.5), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.1875, 0.375, 0.8125, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.0625, 0.375, 0.8125, 0.125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.75, 0.375, 0.8125, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.6875, 0.9375, 0.8125, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.5, 0.9375, 0.8125, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.3125, 0.9375, 0.8125, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.125, 0.9375, 0.8125, 0.25), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.625, 0.75, 0.875, 0.9375, 0.8125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.9375, 0.75, 0.0625, 1, 1, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.75, 0.9375, 1, 1, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 0.0625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.8125, 0.0625, 0.5625, 0.875, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.75, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.75, 0.0625, 0.0625, 1, 0.9375), BooleanOp.OR);
            }
        }

        return shape;
    }

}
