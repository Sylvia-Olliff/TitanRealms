package sylvantus.titanrealms.common.resources;


import com.mojang.serialization.Codec;
import net.minecraft.util.IStringSerializable;
import org.lwjgl.system.CallbackI;
import sylvantus.titanrealms.core.enums.DimensionRestrictions;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum OreType implements IStringSerializable {
    COPPER(PrimaryResource.COPPER, 16, 8, 0, 0, 60),
    ALLARUM(PrimaryResource.ALLARUM, 10, 4, 8, 0, 120, DimensionRestrictions.TR_DIMENSIONS),
    AESIRITE(PrimaryResource.AESIRITE, 4, 3, 0, 0, 120, DimensionRestrictions.AESIR),
    HELUMITE(PrimaryResource.HELUMITE, 4, 3, 0, 0, 16, DimensionRestrictions.HEL);

    public static Codec<OreType> CODEC = IStringSerializable.createEnumCodec(OreType::values, OreType::byName);
    private static final Map<String, OreType> NAME_LOOKUP = Arrays.stream(values()).collect(Collectors.toMap(OreType::getString, oreType -> oreType));

    private final IResource resource;
    private final DimensionRestrictions dimensionRestrictions;
    private final int perChunk;
    private final int maxVeinSize;
    private final int bottomOffset;
    private final int topOffset;
    private final int maxHeight;
    private final int minExp;
    private final int maxExp;

    OreType(IResource resource, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight) {
        this(resource, perChunk, maxVeinSize, bottomOffset, topOffset, maxHeight, 0, 0, DimensionRestrictions.ANY);
    }

    OreType(IResource resource, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight, DimensionRestrictions restrictions) {
        this(resource, perChunk, maxVeinSize, bottomOffset, topOffset, maxHeight, 0, 0, restrictions);
    }

    OreType(IResource resource, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight, int minExp, int maxExp) {
        this(resource, perChunk, maxVeinSize, bottomOffset, topOffset, maxHeight, minExp, maxExp, DimensionRestrictions.ANY);
    }

    OreType(IResource resource, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight, int minExp, int maxExp, DimensionRestrictions restrictions) {
        this.resource = resource;
        this.perChunk = perChunk;
        this.maxVeinSize = maxVeinSize;
        this.bottomOffset = bottomOffset;
        this.topOffset = topOffset;
        this.maxHeight = maxHeight;
        this.minExp = minExp;
        this.maxExp = maxExp;
        this.dimensionRestrictions = restrictions;
    }

    public IResource getResource() {
        return resource;
    }

    public DimensionRestrictions getAllowedDimensions() {
        return dimensionRestrictions;
    }

    public int getPerChunk() {
        return perChunk;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getBottomOffset() {
        return bottomOffset;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinExp() {
        return minExp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public static OreType get(IResource resource) {
        for (OreType ore : values()) {
            if (resource == ore.resource) {
                return ore;
            }
        }
        return null;
    }

    @Nonnull
    @Override
    public String getString() {
        return resource.getRegistrySuffix();
    }

    @Nullable
    private static OreType byName(String name) {
        return NAME_LOOKUP.get(name);
    }
}
