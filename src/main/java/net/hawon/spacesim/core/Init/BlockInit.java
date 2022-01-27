package net.hawon.spacesim.core.Init;

import net.hawon.spacesim.SpaceSim;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.hawon.spacesim.core.Init.ItemInit.fromBlock;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SpaceSim.MOD_ID);
    private static final BlockBehaviour.Properties ORE_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f);

    public static final RegistryObject<Block> DOCKING_PORT = BLOCKS.register("docking_port", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Item> DOCKING_PORT_ITEM = fromBlock(DOCKING_PORT);

    public static final RegistryObject<Block> BAUXITE_ORE = BLOCKS.register("bauxite_ore", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Item> BAUXITE_ORE_ITEM = fromBlock(BAUXITE_ORE);

    public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Item> TITANIUM_ORE_ITEM = fromBlock(TITANIUM_ORE);

    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Item> URANIUM_ORE_ITEM = fromBlock(URANIUM_ORE);

    public static final RegistryObject<Block> EXAMPLE_CHEST = BLOCKS.register("example_chest", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Item> EXAMPLE_CHEST_ITEM = fromBlock(EXAMPLE_CHEST);

}
