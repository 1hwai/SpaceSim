package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.core.Init.BlockInit;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SpaceItemModels extends ItemModelProvider {

    public SpaceItemModels(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, SpaceSim.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        //Long as Snake, but for the line, I eventually gave up. Just for easier scroll.

        //BLOCK ENTITY
        withExistingParent(BlockInit.CRUSHER.get().getRegistryName().getPath(), modLoc("block/crusher"));
        withExistingParent(BlockInit.DOCKING_PORT_ITEM.get().getRegistryName().getPath(), modLoc("block/docking_port"));
        withExistingParent(BlockInit.EXAMPLE_CHEST_ITEM.get().getRegistryName().getPath(), modLoc("block/example_chest"));

        //ORE
        withExistingParent(BlockInit.BAUXITE_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/bauxite_ore"));
        withExistingParent(BlockInit.TITANIUM_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/titanium_ore"));
        withExistingParent(BlockInit.URANIUM_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/uranium_ore"));
        withExistingParent(BlockInit.DEEPSLATE_BAUXITE_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/deepslate_bauxite_ore"));
        withExistingParent(BlockInit.DEEPSLATE_TITANIUM_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/deepslate_titanium_ore"));
        withExistingParent(BlockInit.DEEPSLATE_URANIUM_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/deepslate_uranium_ore"));

        //SINGLE TEXTURE ITEM
        singleTexture(ItemInit.RENCH.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("items/rench"));
        singleTexture(ItemInit.GALVANOMETER.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("items/galvanometer"));


        singleTexture(ItemInit.TITANIUM_INGOT.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("items/titanium_ingot"));
        singleTexture(ItemInit.TITANIUM_DUST.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("items/titanium_dust"));


        singleTexture(ItemInit.RAW_BAUXITE.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("items/raw_bauxite"));
        singleTexture(ItemInit.RAW_TITANIUM.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("items/raw_titanium"));
        singleTexture(ItemInit.RAW_URANIUM.get().getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("items/raw_uranium"));

    }

    @Override
    public String getName() {
        return "SpaceSim Tags";
    }

}
