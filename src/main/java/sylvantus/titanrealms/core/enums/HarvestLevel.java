package sylvantus.titanrealms.core.enums;

public enum HarvestLevel {
    WOOD(0),
    GOLD(0),
    STONE(1),
    IRON(2),
    DIAMOND(3);

    private final int harvestLevel;

    HarvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    public int getLevel() { return this.harvestLevel; }
}
