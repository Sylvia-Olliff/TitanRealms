package sylvantus.titanrealms.common.resources;

import com.mojang.serialization.Codec;
import net.minecraft.util.IStringSerializable;
import sylvantus.titanrealms.core.enums.DimensionRestrictions;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TerrainType implements IStringSerializable {
    AESIR_STONE(TerrainResource.AESIR_STONE, DimensionRestrictions.AESIR),
    BLASTED_MARBLE(TerrainResource.BLASTED_MARBLE, DimensionRestrictions.AESIR),
    BLASTED_STONE(TerrainResource.BLASTED_STONE, DimensionRestrictions.AESIR),
    BLASTED_GLASS(TerrainResource.BLASTED_GLASS, DimensionRestrictions.AESIR),
    TITANFORGED_STONE(TerrainResource.TITANFORGED_STONE, DimensionRestrictions.AESIR);

    public static Codec<TerrainType> CODEC = IStringSerializable.createEnumCodec(TerrainType::values, TerrainType::byName);

    private static final Map<String, TerrainType> NAME_LOOKUP = Arrays.stream(values()).collect(Collectors.toMap(TerrainType::getString, terrainType -> terrainType));

    private final TerrainResource terrain;
    private final DimensionRestrictions dimensionRestrictions;

    TerrainType(TerrainResource terrain, DimensionRestrictions dimensionRestrictions) {
        this.terrain = terrain;
        this.dimensionRestrictions = dimensionRestrictions;
    }

    public TerrainResource getTerrain() { return terrain; }

    public DimensionRestrictions getAllowedDimensions() {
        return dimensionRestrictions;
    }

    public static TerrainType get(TerrainResource resource) {
        for (TerrainType type : values()) {
            if (resource == type.terrain) return type;
        }
        return null;
    }

    @Nonnull
    @Override
    public String getString() { return terrain.getRegistrySuffix(); }

    @Nullable
    private static TerrainType byName(String name) { return NAME_LOOKUP.get(name); }
}
