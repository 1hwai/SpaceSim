package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class SpaceBlockTags extends BlockTagsProvider {

    public SpaceBlockTags(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, SpaceSim.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockInit.BAUXITE_ORE.get())
                .add(BlockInit.TITANIUM_ORE.get())
                .add(BlockInit.URANIUM_ORE.get())
                .add(BlockInit.DEEPSLATE_BAUXITE_ORE.get())
                .add(BlockInit.DEEPSLATE_TITANIUM_ORE.get())
                .add(BlockInit.DEEPSLATE_URANIUM_ORE.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(BlockInit.BAUXITE_ORE.get())
                .add(BlockInit.TITANIUM_ORE.get())
                .add(BlockInit.URANIUM_ORE.get())
                .add(BlockInit.DEEPSLATE_BAUXITE_ORE.get())
                .add(BlockInit.DEEPSLATE_TITANIUM_ORE.get())
                .add(BlockInit.DEEPSLATE_URANIUM_ORE.get());
        tag(Tags.Blocks.ORES)
                .add(BlockInit.BAUXITE_ORE.get())
                .add(BlockInit.TITANIUM_ORE.get())
                .add(BlockInit.URANIUM_ORE.get())
                .add(BlockInit.DEEPSLATE_BAUXITE_ORE.get())
                .add(BlockInit.DEEPSLATE_TITANIUM_ORE.get())
                .add(BlockInit.DEEPSLATE_URANIUM_ORE.get());

        tag(BlockInit.BAUXITE_ORE_TAG)
                .add(BlockInit.BAUXITE_ORE.get())
                .add(BlockInit.DEEPSLATE_BAUXITE_ORE.get());
        tag(BlockInit.TITANIUM_ORE_TAG)
                .add(BlockInit.TITANIUM_ORE.get())
                .add(BlockInit.DEEPSLATE_TITANIUM_ORE.get());
        tag(BlockInit.URANIUM_ORE_TAG)
                .add(BlockInit.URANIUM_ORE.get())
                .add(BlockInit.DEEPSLATE_URANIUM_ORE.get());
    }

    @Override
    public String getName() {
        return "SpaceSim Tags";
    }

}
