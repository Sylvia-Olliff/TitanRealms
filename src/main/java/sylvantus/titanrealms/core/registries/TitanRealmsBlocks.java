package sylvantus.titanrealms.core.registries;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.blocks.AesirDirtBlock;
import sylvantus.titanrealms.core.registration.impl.BlockDeferredRegister;
import sylvantus.titanrealms.core.registration.impl.BlockRegistryObject;

public class TitanRealmsBlocks {

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(TitanRealms.MODID);

    // Aesir Blocks

    public static final BlockRegistryObject<Block, BlockItem> AESIR_DIRT = BLOCKS.register("aesir_dirt", () -> new AesirDirtBlock(AbstractBlock.Properties.create(Material.EARTH).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
    public static final BlockRegistryObject<Block, BlockItem> AESIR_DIRT_GRASS = BLOCKS.register("aesir_dirt_grass", () -> new AesirDirtBlock(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.7F).sound(SoundType.WET_GRASS)));

}
