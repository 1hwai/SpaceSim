package net.hawon.spacesim.core.event;


import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.recipe.CrusherRecipe;
import net.hawon.spacesim.core.world.OreGeneration;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = SpaceSim.MOD_ID, bus = Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void commonSetup(@NotNull FMLCommonSetupEvent event) {
        event.enqueueWork(OreGeneration::registerOres);
        event.enqueueWork(PacketHandler::init);
    }

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, CrusherRecipe.Type.ID, CrusherRecipe.Type.INSTANCE);
    }

}
