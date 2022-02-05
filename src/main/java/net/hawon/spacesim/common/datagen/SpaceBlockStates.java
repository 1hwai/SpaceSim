package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SpaceBlockStates extends BlockStateProvider {

    public SpaceBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, SpaceSim.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {

        //BLOCK ENTITY
        simpleBlock(BlockInit.GENERATOR.get());
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

}
