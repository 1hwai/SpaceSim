package net.hawon.spacesim.client.event;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.client.screen.CrusherScreen;
import net.hawon.spacesim.client.screen.ExampleChestScreen;
import net.hawon.spacesim.client.screen.GeneratorScreen;
import net.hawon.spacesim.core.Init.BlockInit;
import net.hawon.spacesim.core.Init.ContainerInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SpaceSim.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

    private ClientModEvents() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ContainerInit.EXAMPLE_CHEST.get(), ExampleChestScreen::new);
        MenuScreens.register(ContainerInit.GENERATOR.get(), GeneratorScreen::new);
        MenuScreens.register(ContainerInit.CRUSHER.get(), CrusherScreen::new);
    }

}