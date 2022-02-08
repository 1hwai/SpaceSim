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
        registerGenerator();
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

    protected void registerGenerator() {
        //State: ON
        BlockModelBuilder genOn = models().getBuilder("block/generator/generator_on");
        genOn.parent(models().getExistingFile(mcLoc("orientable")));
        genOn.texture("side", "block/generator_side")
                .texture("front", "block/generator_on")
                .texture("top", "block/generator_side");

        //State: IDLE
        BlockModelBuilder genIdle = models().getBuilder("block/generator/generator_idle");
        genIdle.parent(models().getExistingFile(mcLoc("orientable")));
        genIdle.texture("side", "block/generator_side")
                .texture("front", "block/generator_idle")
                .texture("top", "block/generator_side");

        VariantBlockStateBuilder bld = getVariantBuilder(BlockInit.GENERATOR.get());

        BlockModelBuilder[] models = new BlockModelBuilder[] { genOn, genIdle };

        for (int i = 0; i < 2; i++) {
            boolean powered = i == 0;
            bld.partialState().with(FACING, Direction.NORTH)
                    .with(BlockStateProperties.POWERED, powered).modelForState().modelFile(models[i]).addModel();
            bld.partialState().with(FACING, Direction.SOUTH)
                    .with(BlockStateProperties.POWERED, powered).modelForState().modelFile(models[i]).rotationY(180).addModel();
            bld.partialState().with(FACING, Direction.WEST)
                    .with(BlockStateProperties.POWERED, powered).modelForState().modelFile(models[i]).rotationY(270).addModel();
            bld.partialState().with(FACING, Direction.EAST)
                    .with(BlockStateProperties.POWERED, powered).modelForState().modelFile(models[i]).rotationY(90).addModel();
        }
    }

}
