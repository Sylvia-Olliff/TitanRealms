package sylvantus.titanrealms.core.registries;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Direction;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.client.SpecialColors;
import sylvantus.titanrealms.common.blocks.basic.BlockFlammable;
import sylvantus.titanrealms.common.blocks.basic.BlockLog;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrain;
import sylvantus.titanrealms.common.blocks.basic.BlockTerrainSoil;
import sylvantus.titanrealms.common.items.block.*;
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
                if (terrain.getTerrain().getTerrainBlockInfo().isSoil()) {
                    TERRAIN.put(terrain, registerTerrainSoilBlock(terrain.getTerrain().getTerrainBlockInfo()));
                } else {
                    TERRAIN.put(terrain, registerTerrainBlock(terrain.getTerrain().getTerrainBlockInfo()));
                }
            }
        }
    }

    // Aesir Blocks
    public static final BlockRegistryObject<BlockLog, ItemBlockLog> AIRWOOD_LOG = registerLogs("airwood_log", MaterialColor.CLAY, MaterialColor.LIGHT_GRAY_TERRACOTTA);
    public static final BlockRegistryObject<BlockFlammable, ItemBlockPlanks> AIRWOOD_PLANKS = registerWoodenPlanks("airwood_planks", Material.WOOD, MaterialColor.LIGHT_GRAY_TERRACOTTA, 5, 5);
    
    public static final BlockRegistryObject<BlockLog, ItemBlockLog> STORMWOOD_LOG = registerLogs("stormwood_log", MaterialColor.BLUE_TERRACOTTA, MaterialColor.GRAY);
    public static final BlockRegistryObject<BlockFlammable, ItemBlockPlanks> STORMWOOD_PLANKS = registerWoodenPlanks("stormwood_planks", Material.WOOD, MaterialColor.GRAY, 5, 5);

    private static BlockRegistryObject<BlockTerrain, ItemBlockTerrain> registerTerrainBlock(BlockTerrainInfo terrain) {
        return BLOCKS.registerDefaultProperties("block_" + terrain.getRegistrySuffix(), () -> new BlockTerrain(terrain), (block, properties) -> {
            if (!block.getTerrainInfo().burnsInFire()) {
                properties = properties.isImmuneToFire();
            }
            return new ItemBlockTerrain(block, properties);
        });
    }

    private static BlockRegistryObject<BlockTerrainSoil, ItemBlockTerrainSoil> registerTerrainSoilBlock(BlockTerrainInfo terrain) {
        return BLOCKS.registerDefaultProperties("block_" + terrain.getRegistrySuffix(), () -> new BlockTerrainSoil(terrain), ItemBlockTerrainSoil::new);
    }

    private static AbstractBlock.Properties logProperties(MaterialColor top, MaterialColor side) {
        return AbstractBlock.Properties.create(Material.WOOD, (state) ->
                state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? top : side);
    }

    private static BlockRegistryObject<BlockLog, ItemBlockLog> registerLogs(String registryName, MaterialColor top, MaterialColor side) {
        return BLOCKS.registerDefaultProperties("block_" + registryName,
                () -> new BlockLog(logProperties(top, side).hardnessAndResistance(2.0f).sound(SoundType.WOOD)),
                ItemBlockLog::new);
    }

    private static BlockRegistryObject<BlockFlammable, ItemBlockPlanks> registerWoodenPlanks(String registryName, Material material, MaterialColor color, int flammability, int spreadSpeed) {
        return BLOCKS.registerDefaultProperties("block_" + registryName,
                () -> new BlockFlammable(flammability, spreadSpeed, AbstractBlock.Properties.create(material, color).hardnessAndResistance(2.0f).sound(SoundType.WOOD)),
                ItemBlockPlanks::new);
    }
}
