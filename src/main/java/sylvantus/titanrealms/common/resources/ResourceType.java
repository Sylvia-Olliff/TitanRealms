package sylvantus.titanrealms.common.resources;

public enum ResourceType {
    DUST("dust"),
    INGOT("ingot"),
    NUGGET("nugget"),
    INFUSED("infused", "infused"),
    REFINED("refined", "refined");

    private final String registryPrefix;
    private final String pluralPrefix;

    ResourceType(String prefix) {
        this(prefix, prefix + "s");
    }

    ResourceType(String prefix, String pluralPrefix) {
        this.registryPrefix = prefix;
        this.pluralPrefix = pluralPrefix;
    }

    public String getRegistryPrefix() {
        return registryPrefix;
    }

    public String getPluralPrefix() {
        return pluralPrefix;
    }

    public boolean usedByPrimary() {
        // TODO: Not sure why this delineation is used in Mekanism
        return this != INFUSED && this != REFINED;
    }
}
