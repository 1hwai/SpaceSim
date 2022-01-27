package net.hawon.spacesim.core.event;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.core.world.OreGeneration;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SpaceSim.MOD_ID, bus = Bus.FORGE)
public class CommonForgeEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void biomLoading(@NotNull BiomeLoadingEvent event) {
        final List<Supplier<PlacedFeature>> features = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
        OreGeneration.OVERWORLD_ORES.forEach(ore -> features.add(() -> ore));
    }
}