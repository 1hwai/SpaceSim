package net.hawon.spacesim.core.event;


import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.core.world.OreGeneration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = SpaceSim.MOD_ID, bus = Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(OreGeneration::registerOres);
        event.enqueueWork(PacketHandler::init);
    }
}
