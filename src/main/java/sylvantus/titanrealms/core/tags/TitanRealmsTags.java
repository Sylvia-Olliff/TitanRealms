package sylvantus.titanrealms.core.tags;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.resources.*;
import sylvantus.titanrealms.core.enums.EnumUtils;

import java.util.EnumMap;
import java.util.Map;

public class TitanRealmsTags {

    private TitanRealmsTags() {}

    public static class Items {

        private Items() {}

        public static final Table<ResourceType, PrimaryResource, INamedTag<Item>> PROCESSED_RESOURCES = HashBasedTable.create();
        public static final Map<PrimaryResource, INamedTag<Item>> PROCESSED_RESOURCE_BLOCKS = new EnumMap<>(PrimaryResource.class);
        public static final Map<OreType, INamedTag<Item>> ORES = new EnumMap<>(OreType.class);
        public static final Map<TerrainType, INamedTag<Item>> TERRAIN = new EnumMap<>(TerrainType.class);

        static {
            for (PrimaryResource resource : EnumUtils.PRIMARY_RESOURCES) {
                for (ResourceType type : EnumUtils.RESOURCE_TYPES) {
                    if (type.usedByPrimary()) {
                        if (type == ResourceType.INGOT || type == ResourceType.NUGGET || type == ResourceType.DUST) {
                            PROCESSED_RESOURCES.put(type, resource, forgeTag(type.getPluralPrefix() + "/" + resource.name()));
                        } else {
                            PROCESSED_RESOURCES.put(type, resource, tag(type.getPluralPrefix() + "/" + resource.name()));
                        }
                    }
                }
                if (!resource.isVanilla()) {
                    PROCESSED_RESOURCE_BLOCKS.put(resource, forgeTag("storage_blocks/" + resource.name()));
                }
            }
            for (OreType ore : EnumUtils.ORE_TYPES) {
                ORES.put(ore, forgeTag("ores/" + ore.getResource().getRegistrySuffix()));
            }
            for (TerrainType terrainType : EnumUtils.TERRAIN_TYPES) {
                TERRAIN.put(terrainType, forgeTag("block/" + terrainType.getTerrain().getRegistrySuffix()));
            }
        }

        private static INamedTag<Item> forgeTag(String name) {
            return ItemTags.makeWrapperTag("forge:" + name);
        }

        private static INamedTag<Item> tag(String name) {
            return ItemTags.makeWrapperTag(TitanRealms.rl(name).toString());
        }
    }
}
