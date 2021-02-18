package sylvantus.titanrealms.common.resources;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraftforge.common.ToolType;
import sylvantus.titanrealms.core.enums.HarvestLevel;
import sylvantus.titanrealms.core.util.TitanRealmsUtils;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;

import javax.annotation.Nullable;

public enum BlockTerrainInfo implements IResource {
    AESIR_STONE("aesir_stone", 5, 6, HarvestLevel.STONE.getLevel()),
    BLASTED_MARBLE("blasted_marble", 6, 700, HarvestLevel.IRON.getLevel()),
    BLASTED_STONE("blasted_stone", 6, 700, HarvestLevel.IRON.getLevel()),
    BLASTED_GLASS("blasted_glass", 5, 50, HarvestLevel.WOOD.getLevel(), 12, false, false, null, true),
    TITANFORGED_STONE("titanforged_stone", 7, 7, HarvestLevel.IRON.getLevel()),
    SPARSE_CLOUD_SOIL("cloud_soil_sparse", 2, 3, HarvestLevel.WOOD.getLevel(), 9, true, true, ToolType.SHOVEL, true, true, 1),
    CLOUD_SOIL("cloud_soil", 3, 4, HarvestLevel.WOOD.getLevel(), 9, true, true, ToolType.SHOVEL, true, true, 2),
    DENSE_CLOUD_SOIL("cloud_soil_dense", 4, 5, HarvestLevel.WOOD.getLevel(), 9, true, true, ToolType.SHOVEL, true, true, 3);

    private final String registrySuffix;
    private final boolean burnsInFire;
    private final boolean requiresTool;
    private final boolean isTransparent;
    private final boolean isSoil;
    private final float resistance;
    private final float hardness;
    private final int soilBonus;
    private final int lightValue;
    private final int harvestLevel;
    @Nullable
    private final ToolType requiredTool;

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel) {
        this(registrySuffix, hardness, resistance, harvestLevel, 7);
    }

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int lightValue) {
        this(registrySuffix, hardness, resistance, harvestLevel, lightValue, false);
    }

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int lightValue, boolean burnsInFire) {
        this(registrySuffix, hardness, resistance, harvestLevel, lightValue, burnsInFire, true, ToolType.PICKAXE);
    }

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int lightValue, boolean burnsInFire, boolean requiresTool, @Nullable ToolType requiredTool) {
        this(registrySuffix, hardness, resistance, harvestLevel, lightValue, burnsInFire, requiresTool, requiredTool, false);
    }

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int lightValue, boolean burnsInFire, boolean requiresTool, @Nullable ToolType requiredTool, boolean isTransparent) {
        this(registrySuffix, hardness, resistance, harvestLevel, lightValue, burnsInFire, requiresTool, requiredTool, isTransparent, false, 0);
    }

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int lightValue, boolean burnsInFire, boolean requiresTool, @Nullable ToolType requiredTool, boolean isTransparent, boolean isSoil, int soilBonus) {
        this.registrySuffix = registrySuffix;
        this.hardness = hardness;
        this.resistance = resistance;
        this.harvestLevel = harvestLevel;
        this.lightValue = lightValue;
        this.burnsInFire = burnsInFire;
        this.requiresTool = requiresTool;
        this.requiredTool = requiredTool;
        this.isTransparent = isTransparent;
        this.isSoil = isSoil;
        this.soilBonus = soilBonus;
    }

    @Override
    public String getRegistrySuffix() { return registrySuffix; }

    public float getHardness() { return hardness; }

    public float getResistance() { return resistance; }

    public int getHarvestLevel() { return harvestLevel; }

    public boolean requiresTool() { return requiresTool; }

    @Nullable
    public ToolType getRequiredTool() { return requiredTool; }

    public int getLightValue() { return lightValue; }

    public boolean isTransparent() { return isTransparent; }

    public boolean isPortalFrame() { return false; }

    public boolean isSoil() { return isSoil; }

    public int getSoilBonus() { return isSoil ? soilBonus : 0; }

    public int getBurnTime() { return -1; }

    public boolean burnsInFire() { return burnsInFire; }

    public PushReaction getPushReaction() { return PushReaction.NORMAL; }

    public AbstractBlock.Properties buildProperties() {

        if (isSoil) {
            if (requiresTool && requiredTool != null) {
                if (isTransparent) {
                    return AbstractBlock.Properties.create(Material.GLASS)
                            .hardnessAndResistance(hardness, resistance)
                            .setLightLevel(state -> lightValue)
                            .harvestLevel(harvestLevel)
                            .setRequiresTool()
                            .harvestTool(requiredTool)
                            .notSolid()
                            .setAllowsSpawn(TitanRealmsUtils::neverAllowSpawn)
                            .setOpaque(TitanRealmsUtils::isntSolid)
                            .setSuffocates(TitanRealmsUtils::isntSolid)
                            .setBlocksVision(TitanRealmsUtils::isntSolid)
                            .sound(SoundType.CROP);
                } else {
                    return AbstractBlock.Properties.create(Material.EARTH)
                            .hardnessAndResistance(hardness, resistance)
                            .setLightLevel(state -> lightValue)
                            .harvestLevel(harvestLevel)
                            .setRequiresTool()
                            .harvestTool(requiredTool)
                            .sound(SoundType.CROP);
                }
            } else {
                return AbstractBlock.Properties.create(Material.EARTH)
                        .hardnessAndResistance(hardness, resistance)
                        .setLightLevel(state -> lightValue)
                        .harvestLevel(harvestLevel)
                        .sound(SoundType.CROP);
            }
        } else if (isTransparent) {
            return AbstractBlock.Properties.create(Material.GLASS)
                    .hardnessAndResistance(hardness, resistance)
                    .setLightLevel(state -> lightValue)
                    .harvestLevel(harvestLevel)
                    .notSolid()
                    .setAllowsSpawn(TitanRealmsUtils::neverAllowSpawn)
                    .setOpaque(TitanRealmsUtils::isntSolid)
                    .setSuffocates(TitanRealmsUtils::isntSolid)
                    .setBlocksVision(TitanRealmsUtils::isntSolid)
                    .sound(SoundType.GLASS);
        } else if (requiresTool && requiredTool != null) {
            return AbstractBlock.Properties.create(Material.EARTH)
                    .hardnessAndResistance(hardness, resistance)
                    .setLightLevel(state -> lightValue)
                    .setRequiresTool()
                    .harvestTool(requiredTool)
                    .harvestLevel(harvestLevel)
                    .sound(SoundType.STONE);
        } else {
            return AbstractBlock.Properties.create(Material.EARTH)
                    .hardnessAndResistance(hardness, resistance)
                    .setLightLevel(state -> lightValue)
                    .harvestLevel(harvestLevel)
                    .sound(SoundType.GROUND);
        }
    }
}
