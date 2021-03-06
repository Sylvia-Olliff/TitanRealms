package sylvantus.titanrealms.datagen.common.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.resources.TerrainType;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;
import sylvantus.titanrealms.core.tags.TitanRealmsTags;

import javax.annotation.Nullable;

public class TitanRealmsTagProvider extends BaseTagProvider {

    public TitanRealmsTagProvider(DataGenerator gen, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, TitanRealms.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        addTerrainTags();
        addWoodenTags();
    }

    private void addTerrainTags() {
        addToTag(Tags.Blocks.DIRT,
                TitanRealmsBlocks.TERRAIN.get(TerrainType.SPARSE_CLOUD_SOIL),
                TitanRealmsBlocks.TERRAIN.get(TerrainType.CLOUD_SOIL),
                TitanRealmsBlocks.TERRAIN.get(TerrainType.DENSE_CLOUD_SOIL));

        addToTags(Tags.Items.STONE, Tags.Blocks.STONE,
                TitanRealmsBlocks.TERRAIN.get(TerrainType.AESIR_STONE),
                TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_STONE));

        addToTags(ItemTags.STONE_CRAFTING_MATERIALS, Tags.Blocks.STONE,
                TitanRealmsBlocks.TERRAIN.get(TerrainType.AESIR_STONE),
                TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_STONE));

        addToTags(ItemTags.STONE_TOOL_MATERIALS, Tags.Blocks.STONE,
                TitanRealmsBlocks.TERRAIN.get(TerrainType.AESIR_STONE));

        addToTag(Tags.Blocks.GLASS, TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_GLASS));

        addToTags(TitanRealmsTags.Items.TITANFORGED_ITEMS, TitanRealmsTags.Blocks.TITANFORGED_BLOCKS,
                TitanRealmsBlocks.TERRAIN.get(TerrainType.TITANFORGED_STONE));
    }

    private void addWoodenTags() {
        addToTags(ItemTags.WOODEN_FENCES, BlockTags.WOODEN_FENCES,
                TitanRealmsBlocks.AIRWOOD_FENCE);
        addToTags(Tags.Items.FENCES_WOODEN, Tags.Blocks.FENCES_WOODEN,
                TitanRealmsBlocks.AIRWOOD_FENCE);
        addToTag(BlockTags.FENCE_GATES,
                TitanRealmsBlocks.AIRWOOD_GATE);
        addToTags(Tags.Items.FENCE_GATES_WOODEN, Tags.Blocks.FENCE_GATES_WOODEN,
                TitanRealmsBlocks.AIRWOOD_GATE);

        addToTags(ItemTags.PLANKS, BlockTags.PLANKS,
                TitanRealmsBlocks.AIRWOOD_PLANKS,
                TitanRealmsBlocks.STORMWOOD_PLANKS);

        addToTags(ItemTags.STAIRS, BlockTags.STAIRS,
                TitanRealmsBlocks.AIRWOOD_STAIRS);
        addToTags(ItemTags.WOODEN_STAIRS, BlockTags.WOODEN_STAIRS,
                TitanRealmsBlocks.AIRWOOD_STAIRS);

        addToTags(ItemTags.SLABS, BlockTags.SLABS,
                TitanRealmsBlocks.AIRWOOD_SLAB);
        addToTags(ItemTags.WOODEN_SLABS, BlockTags.WOODEN_SLABS,
                TitanRealmsBlocks.AIRWOOD_SLAB);

        addToTags(ItemTags.LOGS, BlockTags.LOGS,
                TitanRealmsBlocks.AIRWOOD_LOG,
                TitanRealmsBlocks.STORMWOOD_LOG);

        addToTags(ItemTags.LOGS_THAT_BURN, BlockTags.LOGS_THAT_BURN,
                TitanRealmsBlocks.AIRWOOD_LOG,
                TitanRealmsBlocks.STORMWOOD_LOG);

        addToTags(ItemTags.WOODEN_PRESSURE_PLATES, BlockTags.WOODEN_PRESSURE_PLATES,
                TitanRealmsBlocks.AIRWOOD_PLATE);

        addToTags(ItemTags.BUTTONS, BlockTags.BUTTONS,
                TitanRealmsBlocks.AIRWOOD_BUTTON);
        addToTags(ItemTags.WOODEN_BUTTONS, BlockTags.WOODEN_BUTTONS,
                TitanRealmsBlocks.AIRWOOD_BUTTON);
    }
}
