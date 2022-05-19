package net.hawon.spacesim.core.Init;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.block.machines.generator.GeneratorCC.GeneratorCCBlock;
import net.hawon.spacesim.common.block.machines.generator.GeneratorOM.GeneratorOMBlock;
import net.hawon.spacesim.common.block.pipe.cables.CopperCableBlock;
import net.hawon.spacesim.common.block.machines.crusher.CrusherBlock;
import net.hawon.spacesim.common.block.storage.examplechest.ExampleChestBlock;
import net.hawon.spacesim.common.block.generator.GeneratorBlock;
import net.hawon.spacesim.common.energy.ThreePhaseType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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

    //BLOCK ENTITY
    public static final RegistryObject<ExampleChestBlock> EXAMPLE_CHEST = BLOCKS.register("example_chest", () -> new ExampleChestBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> EXAMPLE_CHEST_ITEM = fromBlock(EXAMPLE_CHEST);

    public static final RegistryObject<GeneratorBlock> GENERATOR = BLOCKS.register("generator", () -> new GeneratorBlock(
            BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(2.0f).lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0))
    );
    public static final RegistryObject<Item> GENERATOR_ITEM = fromBlock(GENERATOR);

    public static final RegistryObject<CrusherBlock> CRUSHER = BLOCKS.register("crusher", () -> new CrusherBlock(ORE_PROPERTIES.dynamicShape()));
    public static final RegistryObject<Item> CRUSHER_ITEM = fromBlock(CRUSHER);

    public static final RegistryObject<CopperCableBlock> COPPER_CABLE = BLOCKS.register("copper_cable", CopperCableBlock::new);
    public static final RegistryObject<Item> COPPER_CABLE_ITEM = fromBlock(COPPER_CABLE);

    public static final RegistryObject<GeneratorCCBlock> GENERATOR_CC = BLOCKS.register("generator_cc", () -> new GeneratorCCBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> GENERATOR_CC_ITEM = fromBlock(GENERATOR_CC);

    //Generator OM
    public static final RegistryObject<GeneratorOMBlock> GENERATOR_OM_L1 = BLOCKS.register("generator_om_l_one", () -> new GeneratorOMBlock(ORE_PROPERTIES, ThreePhaseType.L1));
    public static final RegistryObject<Item> GENERATOR_OM_L1_ITEM = fromBlock(GENERATOR_OM_L1);

    public static final RegistryObject<GeneratorOMBlock> GENERATOR_OM_L2 = BLOCKS.register("generator_om_l_two", () -> new GeneratorOMBlock(ORE_PROPERTIES, ThreePhaseType.L2));
    public static final RegistryObject<Item> GENERATOR_OM_L2_ITEM = fromBlock(GENERATOR_OM_L2);

    public static final RegistryObject<GeneratorOMBlock> GENERATOR_OM_L3 = BLOCKS.register("generator_om_l_three", () -> new GeneratorOMBlock(ORE_PROPERTIES, ThreePhaseType.L3));
    public static final RegistryObject<Item> GENERATOR_OM_L3_ITEM = fromBlock(GENERATOR_OM_L3);

    public static final RegistryObject<GeneratorOMBlock> GENERATOR_OM_N = BLOCKS.register("generator_om_l_n", () -> new GeneratorOMBlock(ORE_PROPERTIES, ThreePhaseType.N));
    public static final RegistryObject<Item> GENERATOR_OM_N_ITEM = fromBlock(GENERATOR_OM_N);


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
