package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.hawon.spacesim.core.Init.BlockInit;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.data.DataGenerator;

public class SpaceLootTables extends BaseLootTables {

    public SpaceLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(BlockInit.GENERATOR.get(), createStandardTable("generator", BlockInit.GENERATOR.get(), BlockEntityInit.GENERATOR.get()));
        lootTables.put(BlockInit.BAUXITE_ORE.get(), createSilkTouchTable("bauxite_ore", BlockInit.BAUXITE_ORE.get(), ItemInit.RAW_BAUXITE.get(), 1, 1));
        lootTables.put(BlockInit.TITANIUM_ORE.get(), createSilkTouchTable("titanium_ore", BlockInit.TITANIUM_ORE.get(), ItemInit.RAW_TITANIUM.get(), 1, 1));
        lootTables.put(BlockInit.URANIUM_ORE.get(), createSilkTouchTable("uranium_ore", BlockInit.URANIUM_ORE.get(), ItemInit.RAW_URANIUM.get(), 1, 1));
        lootTables.put(BlockInit.DEEPSLATE_BAUXITE_ORE.get(), createSilkTouchTable("deepslate_bauxite_ore", BlockInit.DEEPSLATE_BAUXITE_ORE.get(), ItemInit.RAW_BAUXITE.get(), 1, 1));
        lootTables.put(BlockInit.DEEPSLATE_TITANIUM_ORE.get(), createSilkTouchTable("deepslate_titanium_ore", BlockInit.DEEPSLATE_TITANIUM_ORE.get(), ItemInit.RAW_TITANIUM.get(), 1, 1));
        lootTables.put(BlockInit.DEEPSLATE_URANIUM_ORE.get(), createSilkTouchTable("deepslate_uranium_ore", BlockInit.DEEPSLATE_URANIUM_ORE.get(), ItemInit.RAW_URANIUM.get(), 1, 1));
    }
}
