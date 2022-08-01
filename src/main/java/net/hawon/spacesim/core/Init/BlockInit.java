package net.hawon.spacesim.core.Init;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.block.edges.cables.CableBlock;
import net.hawon.spacesim.common.block.edges.cables.CableType;
import net.hawon.spacesim.common.block.nodes.generator.TestgenBlock;
import net.hawon.spacesim.common.block.nodes.crusher.CrusherBlock;
import net.hawon.spacesim.common.block.storage.examplechest.ExampleChestBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.hawon.spacesim.core.Init.ItemInit.fromBlock;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SpaceSim.MOD_ID);
    private static final BlockBehaviour.Properties ORE_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f);
    private static final BlockBehaviour.Properties DEEPSLATE_ORE_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(4f);

    public static final RegistryObject<Block> DOCKING_PORT = BLOCKS.register("docking_port", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Item> DOCKING_PORT_ITEM = fromBlock(DOCKING_PORT);

    //BE
    public static final RegistryObject<ExampleChestBlock> EXAMPLE_CHEST = BLOCKS.register("example_chest", () -> new ExampleChestBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> EXAMPLE_CHEST_ITEM = fromBlock(EXAMPLE_CHEST);

    //Test
    public static final RegistryObject<TestgenBlock> TESTGEN = BLOCKS.register("testgen", () -> new TestgenBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> TESTGEN_ITEM = fromBlock(TESTGEN);

    public static final RegistryObject<CrusherBlock> CRUSHER = BLOCKS.register("crusher", () -> new CrusherBlock(ORE_PROPERTIES.dynamicShape()));
    public static final RegistryObject<Item> CRUSHER_ITEM = fromBlock(CRUSHER);

    //Cables
    public static final RegistryObject<CableBlock> COPPER_CABLE_MID = BLOCKS.register(CableType.COPPER_MID.toString(), () -> new CableBlock(CableType.COPPER_MID));
    public static final RegistryObject<Item> COPPER_CABLE_MID_ITEM = fromBlock(COPPER_CABLE_MID);
    public static final RegistryObject<CableBlock> ALUMINUM_CABLE_MID = BLOCKS.register(CableType.ALUMINIUM_MID.toString(), () -> new CableBlock(CableType.ALUMINIUM_MID));
    public static final RegistryObject<Item> ALUMINUM_CABLE_MID_ITEM = fromBlock(ALUMINUM_CABLE_MID);
    public static final RegistryObject<CableBlock> TIN_CABLE_MID = BLOCKS.register(CableType.TIN_MID.toString(), () -> new CableBlock(CableType.TIN_MID));
    public static final RegistryObject<Item> TIN_CABLE_MID_ITEM = fromBlock(TIN_CABLE_MID);

    //ORE
    public static final RegistryObject<Block> BAUXITE_ORE = BLOCKS.register("bauxite_ore", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Block> DEEPSLATE_BAUXITE_ORE = BLOCKS.register("deepslate_bauxite_ore", () -> new Block(DEEPSLATE_ORE_PROPERTIES));
    public static final RegistryObject<Item> BAUXITE_ORE_ITEM = fromBlock(BAUXITE_ORE);
    public static final RegistryObject<Item> DEEPSLATE_BAUXITE_ORE_ITEM = fromBlock(DEEPSLATE_BAUXITE_ORE);

    public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = BLOCKS.register("deepslate_titanium_ore", () -> new Block(DEEPSLATE_ORE_PROPERTIES));
    public static final RegistryObject<Item> TITANIUM_ORE_ITEM = fromBlock(TITANIUM_ORE);
    public static final RegistryObject<Item> DEEPSLATE_TITANIUM_ORE_ITEM = fromBlock(DEEPSLATE_TITANIUM_ORE);

    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore", () -> new Block(ORE_PROPERTIES));
    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE = BLOCKS.register("deepslate_uranium_ore", () -> new Block(DEEPSLATE_ORE_PROPERTIES));
    public static final RegistryObject<Item> URANIUM_ORE_ITEM = fromBlock(URANIUM_ORE);
    public static final RegistryObject<Item> DEEPSLATE_URANIUM_ORE_ITEM = fromBlock(DEEPSLATE_URANIUM_ORE);

    public static final Tags.IOptionalNamedTag<Block> BAUXITE_ORE_TAG = BlockTags.createOptional(new ResourceLocation(SpaceSim.MOD_ID, "bauxite_ore"));
    public static final Tags.IOptionalNamedTag<Block> TITANIUM_ORE_TAG = BlockTags.createOptional(new ResourceLocation(SpaceSim.MOD_ID, "titanium_ore"));
    public static final Tags.IOptionalNamedTag<Block> URANIUM_ORE_TAG = BlockTags.createOptional(new ResourceLocation(SpaceSim.MOD_ID, "uranium_ore"));

    public static final Tags.IOptionalNamedTag<Item> BAUXITE_ORE_ITEM_TAG = ItemTags.createOptional(new ResourceLocation(SpaceSim.MOD_ID, "bauxite_ore"));
    public static final Tags.IOptionalNamedTag<Item> TITANIUM_ORE_ITEM_TAG = ItemTags.createOptional(new ResourceLocation(SpaceSim.MOD_ID, "titanium_ore"));
    public static final Tags.IOptionalNamedTag<Item> URANIUM_ORE_ITEM_TAG = ItemTags.createOptional(new ResourceLocation(SpaceSim.MOD_ID, "uranium_ore"));

}
