package net.hawon.spacesim.core.Init;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.container.CrusherContainer;
import net.hawon.spacesim.common.container.ExampleChestContainer;
import net.hawon.spacesim.common.container.GeneratorContainer;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
            SpaceSim.MOD_ID);

    private ContainerInit() {}

    public static final RegistryObject<MenuType<ExampleChestContainer>> EXAMPLE_CHEST = CONTAINERS
            .register("example_chest", () -> new MenuType<>(ExampleChestContainer::new));

    public static final RegistryObject<MenuType<GeneratorContainer>> GENERATOR = CONTAINERS
            .register("generator", () -> IForgeMenuType.create((id, inv, data) ->
                    new GeneratorContainer(id, data.readBlockPos(), inv, inv.player))
            );

    public static final RegistryObject<MenuType<CrusherContainer>> CRUSHER = CONTAINERS
            .register("crusher", () -> IForgeMenuType.create((id, inv, data) ->
                    new CrusherContainer(id, data.readBlockPos(), inv, inv.player))
            );


}