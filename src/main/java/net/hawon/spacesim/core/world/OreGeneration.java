package net.hawon.spacesim.core.world;

import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.ArrayList;
import java.util.List;

public class OreGeneration {
    public static final List<PlacedFeature> OVERWORLD_ORES = new ArrayList<>();

    public static void registerOres() {
        //TITANIUM
        final ConfiguredFeature<?, ?> titaniumOre = FeatureUtils.register("titanium_ore", Feature.ORE.configured(new OreConfiguration(List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                        BlockInit.TITANIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        BlockInit.DEEPSLATE_TITANIUM_ORE.get().defaultBlockState())),
                10)));

        final PlacedFeature TITANIUM_ORE = PlacementUtils.register("titanium_ore_middle", titaniumOre.placed(commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(50)))));

        //BAUXITE
        final ConfiguredFeature<?, ?> bauxiteOre = FeatureUtils.register("bauxite_ore", Feature.ORE.configured(new OreConfiguration(List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                        BlockInit.BAUXITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        BlockInit.DEEPSLATE_BAUXITE_ORE.get().defaultBlockState())),
                10)));

        final PlacedFeature BAUXITE_ORE = PlacementUtils.register("bauxite_ore_middle", bauxiteOre.placed(commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(50)))));

        //URANIUM
        final ConfiguredFeature<?, ?> uraniumOre = FeatureUtils.register("uranium_ore", Feature.ORE.configured(new OreConfiguration(List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                        BlockInit.URANIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        BlockInit.DEEPSLATE_URANIUM_ORE.get().defaultBlockState())),
                10)));

        final PlacedFeature URANIUM_ORE = PlacementUtils.register("uranium_ore_middle", uraniumOre.placed(commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

        OVERWORLD_ORES.add(TITANIUM_ORE);
        OVERWORLD_ORES.add(BAUXITE_ORE);
        OVERWORLD_ORES.add(URANIUM_ORE);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

}
