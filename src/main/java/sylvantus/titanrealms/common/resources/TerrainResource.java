package sylvantus.titanrealms.common.resources;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import sylvantus.titanrealms.core.tags.TitanRealmsTags;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;

import java.util.function.Supplier;

public enum TerrainResource implements IResource {
    AESIR_STONE("aesir_stone", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.AESIR_STONE), BlockTerrainInfo.AESIR_STONE),
    BLASTED_MARBLE("blasted_marble", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.BLASTED_MARBLE), BlockTerrainInfo.BLASTED_MARBLE),
    BLASTED_STONE("blasted_stone", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.BLASTED_STONE), BlockTerrainInfo.BLASTED_STONE),
    BLASTED_GLASS("blasted_glass", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.BLASTED_GLASS), BlockTerrainInfo.BLASTED_GLASS),
    TITANFORGED_STONE("titanforged_stone", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.TITANFORGED_STONE), BlockTerrainInfo.TITANFORGED_STONE),
    SPARSE_CLOUD_SOIL("cloud_soil_sparse", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.SPARSE_CLOUD_SOIL), BlockTerrainInfo.SPARSE_CLOUD_SOIL),
    CLOUD_SOIL("cloud_soil", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.CLOUD_SOIL), BlockTerrainInfo.CLOUD_SOIL),
    DENSE_CLOUD_SOIL("cloud_soil_dense", 0xFFAF8E77, () -> TitanRealmsTags.Items.TERRAIN.get(TerrainType.DENSE_CLOUD_SOIL), BlockTerrainInfo.DENSE_CLOUD_SOIL);

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
