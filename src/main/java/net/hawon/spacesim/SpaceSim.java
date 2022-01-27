package net.hawon.spacesim;

import net.hawon.spacesim.core.Init.BlockEntityInit;
import net.hawon.spacesim.core.Init.BlockInit;
import net.hawon.spacesim.core.Init.ContainerInit;
import net.hawon.spacesim.core.Init.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SpaceSim.MOD_ID)
public class SpaceSim
{
    // Directly reference a log4j logger.
    public static final String MOD_ID = "spacesim";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final CreativeModeTab SPACESIM_TAB = new CreativeModeTab("Space Sim") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.RENCH.get());
        }
    };

    public SpaceSim() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();//.addListener(this::setup);

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        ContainerInit.CONTAINERS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

//    private void setup(final FMLCommonSetupEvent event)
//    {
//        // some preinit code
//        LOGGER.info("HELLO FROM PREINIT");
//        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
//    }

}
