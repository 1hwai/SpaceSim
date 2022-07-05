package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class SpaceBlockStates extends BlockStateProvider {

    public SpaceBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, SpaceSim.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {

        //BLOCK ENTITY
        registerCrusher();
        simpleBlock(BlockInit.DOCKING_PORT.get());
        simpleBlock(BlockInit.EXAMPLE_CHEST.get());
        //ORE
        simpleBlock(BlockInit.BAUXITE_ORE.get());
        simpleBlock(BlockInit.URANIUM_ORE.get());
        simpleBlock(BlockInit.TITANIUM_ORE.get());
        simpleBlock(BlockInit.DEEPSLATE_TITANIUM_ORE.get());
        simpleBlock(BlockInit.DEEPSLATE_URANIUM_ORE.get());
        simpleBlock(BlockInit.DEEPSLATE_BAUXITE_ORE.get());
    }

    protected void registerCrusher() {
        BlockModelBuilder crusher = models().getBuilder("block/crusher");

        VariantBlockStateBuilder bld = getVariantBuilder(BlockInit.CRUSHER.get());

        bld.partialState().with(FACING, Direction.NORTH).modelForState().modelFile(crusher).addModel();
        bld.partialState().with(FACING, Direction.SOUTH).modelForState().modelFile(crusher).rotationY(180).addModel();
        bld.partialState().with(FACING, Direction.WEST).modelForState().modelFile(crusher).rotationY(270).addModel();
        bld.partialState().with(FACING, Direction.EAST).modelForState().modelFile(crusher).rotationY(90).addModel();

    }

}
