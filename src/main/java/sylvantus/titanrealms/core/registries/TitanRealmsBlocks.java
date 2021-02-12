package sylvantus.titanrealms.core.registries;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrain;
import sylvantus.titanrealms.common.items.block.ItemBlockTerrain;
import sylvantus.titanrealms.common.resources.BlockTerrainInfo;
import sylvantus.titanrealms.common.resources.TerrainResource;
import sylvantus.titanrealms.common.resources.TerrainType;
import sylvantus.titanrealms.core.enums.EnumUtils;
import sylvantus.titanrealms.core.registration.impl.BlockDeferredRegister;
import sylvantus.titanrealms.core.registration.impl.BlockRegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class TitanRealmsBlocks {

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(TitanRealms.MODID);

    public static final Map<TerrainType, BlockRegistryObject<?, ?>> TERRAIN = new LinkedHashMap<>();

    static {
        // terrain
        for (TerrainType terrain : EnumUtils.TERRAIN_TYPES) {
            if (terrain.getTerrain().getTerrainBlockInfo() != null) {
                TERRAIN.put(terrain, registerTerrainBlock(terrain.getTerrain().getTerrainBlockInfo()));
            }
        }
    }

    // Aesir Blocks

    private static BlockRegistryObject<BlockTerrain, ItemBlockTerrain> registerTerrainBlock(BlockTerrainInfo terrain) {
        return BLOCKS.registerDefaultProperties("block_" + terrain.getRegistrySuffix(), () -> new BlockTerrain(terrain), (block, properties) -> {
            if (!block.getTerrainInfo().burnsInFire()) {
                properties = properties.isImmuneToFire();
            }
            return new ItemBlockTerrain(block, properties);
        });
    }
}
