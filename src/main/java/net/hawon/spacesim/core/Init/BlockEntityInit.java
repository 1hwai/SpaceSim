package net.hawon.spacesim.core.Init;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.block.machines.generator.GeneratorCC.GeneratorCCBlockEntity;
import net.hawon.spacesim.common.block.machines.generator.GeneratorOM.GeneratorOMBlockEntity;
import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.hawon.spacesim.common.block.generator.GeneratorBlockEntity;
import net.hawon.spacesim.common.block.machines.crusher.CrusherBlockEntity;
import net.hawon.spacesim.common.block.storage.examplechest.ExampleChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public final class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, SpaceSim.MOD_ID);

    public static final RegistryObject<BlockEntityType<ExampleChestBlockEntity>> EXAMPLE_CHEST = BLOCK_ENTITIES
            .register("example_chest", () -> BlockEntityType.Builder
                    .of(ExampleChestBlockEntity::new, BlockInit.EXAMPLE_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR = BLOCK_ENTITIES
            .register("generator", () -> BlockEntityType.Builder
                    .of(GeneratorBlockEntity::new, BlockInit.GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER = BLOCK_ENTITIES
            .register("crusher", () -> BlockEntityType.Builder.
                    of(CrusherBlockEntity::new, BlockInit.CRUSHER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CableBlockEntity>> CABLE = BLOCK_ENTITIES
            .register("cable", () -> BlockEntityType.Builder
                    .of(CableBlockEntity::new, BlockInit.COPPER_CABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<GeneratorCCBlockEntity>> GENERATOR_CC = BLOCK_ENTITIES
            .register("generator_cc", () -> BlockEntityType.Builder
                    .of(GeneratorCCBlockEntity::new, BlockInit.GENERATOR_CC.get()).build(null));

    //Generator OM
    public static final RegistryObject<BlockEntityType<GeneratorOMBlockEntity>> GENERATOR_OM_L1 = BLOCK_ENTITIES
            .register("generator_om_l_one", () -> BlockEntityType.Builder
                    .of(GeneratorOMBlockEntity::new, BlockInit.GENERATOR_OM_L1.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorOMBlockEntity>> GENERATOR_OM_L2 = BLOCK_ENTITIES
            .register("generator_om_l_two", () -> BlockEntityType.Builder
                    .of(GeneratorOMBlockEntity::new, BlockInit.GENERATOR_OM_L2.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorOMBlockEntity>> GENERATOR_OM_L3 = BLOCK_ENTITIES
            .register("generator_om_l_three", () -> BlockEntityType.Builder
                    .of(GeneratorOMBlockEntity::new, BlockInit.GENERATOR_OM_L3.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorOMBlockEntity>> GENERATOR_OM_N = BLOCK_ENTITIES
            .register("generator_om_l_n", () -> BlockEntityType.Builder
                    .of(GeneratorOMBlockEntity::new, BlockInit.GENERATOR_OM_N.get()).build(null));

    private BlockEntityInit() {
    }
}