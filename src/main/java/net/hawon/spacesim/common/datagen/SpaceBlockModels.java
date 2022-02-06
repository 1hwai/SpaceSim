package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SpaceBlockModels extends BlockModelProvider {

    public SpaceBlockModels(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, SpaceSim.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
//        orientableVertical("generator", modLoc("block/generator_on"), modLoc("block/generator_side"));
    }

    @Override
    public String getName() {
        return "SpaceSim Tags";
    }

}
