package sylvantus.titanrealms.common.resources;

import net.minecraft.block.material.PushReaction;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;

public enum BlockResourceInfo implements IResource {
    TITAN_BRONZE("titan_bronze", 40, 2_000, 3, -1, 9),
    TITAN_STEEL("titan_steel", 50, 2_800, 4, -1, 7),
    COPPER("copper", 5, 6, 1),
    ALLARUM("allarum", 5,9,1),
    AESIRITE("aesirite", 5, 9, 2),
    HELUMITE("helumite", 5, 9, 2);

    private final String registrySuffix;
    private final PushReaction pushReaction;
    private final boolean portalFrame;
    private final boolean burnsInFire;
    private final float resistance;
    private final float hardness;
    private final int burnTime;
    private final int lightValue;
    private final int harvestLevel;

    BlockResourceInfo(String registrySuffix, float hardness, float resistance, int harvestLevel) {
        this(registrySuffix, hardness, resistance, harvestLevel, -1);
    }

    BlockResourceInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int burnTime) {
        this(registrySuffix, hardness, resistance, harvestLevel, burnTime, 0);
    }

    BlockResourceInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int burnTime, int lightValue) {
        this(registrySuffix, hardness, resistance, harvestLevel, burnTime, lightValue, true, false, PushReaction.NORMAL);
    }

    BlockResourceInfo(String registrySuffix, float hardness, float resistance, int harvestLevel, int burnTime, int lightValue, boolean burnsInFire, boolean portalFrame,
                      PushReaction pushReaction) {
        this.registrySuffix = registrySuffix;
        this.pushReaction = pushReaction;
        this.portalFrame = portalFrame;
        this.burnsInFire = burnsInFire;
        this.burnTime = burnTime;
        this.lightValue = lightValue;
        this.resistance = resistance;
        this.hardness = hardness;
        this.harvestLevel = harvestLevel;
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

    public int getLightValue() {
        return lightValue;
    }

    public boolean isPortalFrame() {
        return portalFrame;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public boolean burnsInFire() {
        return burnsInFire;
    }

    public PushReaction getPushReaction() {
        return pushReaction;
    }
}
