package sylvantus.titanrealms.common.resources;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import sylvantus.titanrealms.core.tags.TitanRealmsTags;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;

import java.util.function.Supplier;

public enum TerrainResource implements IResource {
    AESIR_STONE("aesir_stone", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.AESIR_STONE), BlockTerrainInfo.AESIR_STONE);

    private final String name;
    private final int tint;
    private final Supplier<ITag<Item>> terrainTag;
    private final boolean isVanilla;
    private final BlockTerrainInfo terrainBlockInfo;

    TerrainResource(String name, int tint, ITag<Item> terrainTag) {
        this(name, tint, () -> terrainTag, true, null);
    }

    TerrainResource(String name, int tint, Supplier<ITag<Item>> terrainTag, BlockTerrainInfo terrainBlockInfo) {
        this(name, tint, terrainTag, false, terrainBlockInfo);
    }

    TerrainResource(String name, int tint, Supplier<ITag<Item>> terrainTag, boolean isVanilla, BlockTerrainInfo terrainBlockInfo) {
        this.name = name;
        this.tint = tint;
        this.terrainTag = terrainTag;
        this.isVanilla = isVanilla;
        this.terrainBlockInfo = terrainBlockInfo;
    }

    @Override
    public String getRegistrySuffix() {
        return name;
    }

    public int getTint() {
        return tint;
    }

    public ITag<Item> getTerrainTag() { return terrainTag.get(); }

    public boolean isVanilla() { return isVanilla; }

    public BlockTerrainInfo getTerrainBlockInfo() { return terrainBlockInfo; }
}
