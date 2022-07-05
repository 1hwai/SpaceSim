package net.hawon.spacesim.core.Init;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.block.pipe.cables.CableBE;
import net.hawon.spacesim.common.block.machines.crusher.CrusherBE;
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

    public static final RegistryObject<BlockEntityType<CrusherBE>> CRUSHER = BLOCK_ENTITIES
            .register("crusher", () -> BlockEntityType.Builder.
                    of(CrusherBE::new, BlockInit.CRUSHER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CableBE>> CABLE = BLOCK_ENTITIES
            .register("cable", () -> BlockEntityType.Builder
                    .of(CableBE::new, BlockInit.COPPER_CABLE.get()).build(null));

    private BlockEntityInit() {
    }
}