package sylvantus.titanrealms.common.resources;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraftforge.common.ToolType;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;

import javax.annotation.Nullable;

public enum BlockTerrainInfo implements IResource {
    AESIR_STONE("aesir_stone", 5, 6, 1, 7, false, true, ToolType.PICKAXE);

    private final String registrySuffix;
    private final boolean burnsInFire;
    private final boolean requiresTool;
    private final float resistance;
    private final float hardness;
    private final int lightValue;
    private final int harvestLevel;
    @Nullable
    private final ToolType requiredTool;

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel) {
        this(registrySuffix, hardness, resistance, harvestLevel, 7);
    }

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int lightValue) {
        this(registrySuffix, hardness, resistance, harvestLevel, lightValue, true);
    }

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int lightValue, boolean burnsInFire) {
        this(registrySuffix, hardness, resistance, harvestLevel, lightValue, burnsInFire, false, null);
    }

    BlockTerrainInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int lightValue, boolean burnsInFire, boolean requiresTool, @Nullable ToolType requiredTool) {
        this.registrySuffix = registrySuffix;
        this.hardness = hardness;
        this.resistance = resistance;
        this.harvestLevel = harvestLevel;
        this.lightValue = lightValue;
        this.burnsInFire = burnsInFire;
        this.requiresTool = requiresTool;
        this.requiredTool = requiredTool;
    }

    @Override
    public String getRegistrySuffix() {
        return registrySuffix;
    }

    public float getHardness() {
        return hardness;
    }

    public float getResistance() {
        return resistance;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public boolean requiresTool() { return requiresTool; }

    public ToolType getRequiredTool() { return requiredTool; }

    public int getLightValue() {
        return lightValue;
    }

    public boolean isPortalFrame() {
        return false;
    }

    public int getBurnTime() {
        return -1;
    }

    public boolean burnsInFire() {
        return burnsInFire;
    }

    public PushReaction getPushReaction() {
        return PushReaction.NORMAL;
    }

    public AbstractBlock.Properties buildProperties() {
        if (requiresTool && requiredTool != null) {
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
