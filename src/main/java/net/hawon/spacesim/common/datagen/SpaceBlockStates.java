package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

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
//        {
//            "parent": "minecraft:block/orientable",
//            "textures": {
//                    "side": "spacesim:block/generator_side",
//                    "front": "spacesim:block/generator_on",
//                    "top": "spacesim:block/generator_side"
//            }
//        }
        //horizontalBlock(BlockInit.GENERATOR.get(), modLoc("block/generator_side"), modLoc("block/generator_on"), modLoc("block/generator_side"));
        //Generating
        BlockModelBuilder genOn = models().getBuilder("block/generator/generator_on");
        genOn.parent(models().getExistingFile(mcLoc("orientable")));
        genOn.texture("side", "block/generator_side")
                .texture("front", "block/generator_on")
                .texture("top", "block/generator_side");
        //Not Generating
        BlockModelBuilder genIdle = models().getBuilder("block/generator/generator_idle");
        genIdle.parent(models().getExistingFile(mcLoc("orientable")));
        genIdle.texture("side", "block/generator_side")
                .texture("front", "block/generator_idle")
                .texture("top", "block/generator_side");

        MultiPartBlockStateBuilder bld = getMultipartBuilder(BlockInit.GENERATOR.get());

        BlockModelBuilder[] models = new BlockModelBuilder[] { genOn, genIdle };
        for (int i = 0 ; i < 2 ; i++) {
            boolean powered = i == 1;
            bld.part().modelFile(models[i]).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationX(180).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationX(270).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationY(90).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
            bld.part().modelFile(models[i]).rotationY(270).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
        }


    }

}
