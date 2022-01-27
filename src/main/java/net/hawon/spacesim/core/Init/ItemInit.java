package net.hawon.spacesim.core.Init;

import net.hawon.spacesim.SpaceSim;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SpaceSim.MOD_ID);
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(SpaceSim.SPACESIM_TAB);

    public static final RegistryObject<Item> RENCH = ITEMS.register("rench", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<Item> RAW_BAUXITE = ITEMS.register("raw_bauxite", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<Item> RAW_URANIUM = ITEMS.register("raw_uranium", () -> new Item(ITEM_PROPERTIES));

    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }

}
