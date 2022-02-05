package net.hawon.spacesim.common.datagen;


import net.hawon.spacesim.SpaceSim;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = SpaceSim.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new SpaceLootTables(generator));
            SpaceBlockTags blockTags = new SpaceBlockTags(generator, event.getExistingFileHelper());
            generator.addProvider(blockTags);
            generator.addProvider(new SpaceItemTags(generator, blockTags, event.getExistingFileHelper()));
        }
        if (event.includeClient()) {
            generator.addProvider(new SpaceBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new SpaceItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new SpaceBlockModels(generator, event.getExistingFileHelper()));
        }
    }

}
