package sylvantus.titanrealms.datagen.common.tags;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public final class TagType<TYPE extends IForgeRegistryEntry<TYPE>> {

    public static final TagType<Item> ITEM = new TagType<>("Item", () -> ForgeRegistries.ITEMS);
    public static final TagType<Block> BLOCK = new TagType<>("Block", () -> ForgeRegistries.BLOCKS);
    public static final TagType<EntityType<?>> ENTITY_TYPE = new TagType<>("Entity Type", () -> ForgeRegistries.ENTITIES);
    public static final TagType<Fluid> FLUID = new TagType<>("Fluid", () -> ForgeRegistries.FLUIDS);
    public static final TagType<Enchantment> ENCHANTMENT = new TagType<>("Enchantment", () -> ForgeRegistries.ENCHANTMENTS);
    public static final TagType<Potion> POTION = new TagType<>("Potion", () -> ForgeRegistries.POTION_TYPES);
    public static final TagType<TileEntityType<?>> TILE_ENTITY_TYPE = new TagType<>("Tile Entity Type", () -> ForgeRegistries.TILE_ENTITIES);

    private final NonNullSupplier<IForgeRegistry<TYPE>> registry;
    private final String name;

    private TagType(String name, NonNullSupplier<IForgeRegistry<TYPE>> registry) {
        this.name = name;
        this.registry = registry;
    }

    public String getName() {
        return name;
    }

    public IForgeRegistry<TYPE> getRegistry() {
        return registry.get();
    }
}
