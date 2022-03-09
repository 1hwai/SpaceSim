package net.hawon.spacesim.core.Init;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.block.entity.CopperCableBlockEntity;
import net.hawon.spacesim.common.block.entity.ExampleChestBlockEntity;
import net.hawon.spacesim.common.block.entity.ExampleMachineBlockEntity;
import net.hawon.spacesim.common.block.entity.GeneratorBlockEntity;
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

    public static final RegistryObject<BlockEntityType<ExampleMachineBlockEntity>> EXAMPLE_MACHINE = BLOCK_ENTITIES
            .register("example_machine", () -> BlockEntityType.Builder
                    .of(ExampleMachineBlockEntity::new, BlockInit.EXAMPLE_MACHINE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CopperCableBlockEntity>> COPPER_CABLE = BLOCK_ENTITIES
            .register("copper_cable", () -> BlockEntityType.Builder
                    .of(CopperCableBlockEntity::new, BlockInit.COPPER_CABLE.get()).build(null));

    private BlockEntityInit() {
    }
}