package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.core.Init.BlockInit;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SpaceItemTags extends ItemTagsProvider {
    public SpaceItemTags(DataGenerator gen, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(gen, blockTags, SpaceSim.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Items.ORES)
                .add(BlockInit.BAUXITE_ORE_ITEM.get())
                .add(BlockInit.TITANIUM_ORE_ITEM.get())
                .add(BlockInit.URANIUM_ORE_ITEM.get())
                .add(BlockInit.DEEPSLATE_BAUXITE_ORE_ITEM.get())
                .add(BlockInit.DEEPSLATE_TITANIUM_ORE_ITEM.get())
                .add(BlockInit.DEEPSLATE_URANIUM_ORE_ITEM.get());
        tag(Tags.Items.INGOTS)
                .add(ItemInit.TITANIUM_INGOT.get());

        tag(BlockInit.BAUXITE_ORE_ITEM_TAG)
                .add(BlockInit.BAUXITE_ORE_ITEM.get())
                .add(BlockInit.DEEPSLATE_BAUXITE_ORE_ITEM.get());
        tag(BlockInit.TITANIUM_ORE_ITEM_TAG)
                .add(BlockInit.TITANIUM_ORE_ITEM.get())
                .add(BlockInit.DEEPSLATE_TITANIUM_ORE_ITEM.get());
        tag(BlockInit.URANIUM_ORE_ITEM_TAG)
                .add(BlockInit.URANIUM_ORE_ITEM.get())
                .add(BlockInit.DEEPSLATE_URANIUM_ORE_ITEM.get());

    }

    @Override
    public String getName() {
        return "SpaceSim Tags";
    }
}
