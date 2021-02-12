package sylvantus.titanrealms.datagen.common.loot.table;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mekanism.api.providers.IBlockProvider;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.CopyNbt.Source;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import sylvantus.titanrealms.core.util.interfaces.blocks.IHasTileEntity;
import sylvantus.titanrealms.core.util.interfaces.tiles.ISustainedData;

public abstract class BaseBlockLootTables extends BlockLootTables {
    //Copy of BlockLootTables#SILK_TOUCH
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create()
            .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

    private final Set<Block> knownBlocks = new ObjectOpenHashSet<>();
    private final Set<Block> toSkip = new ObjectOpenHashSet<>();

    @Override
    protected abstract void addTables();

    @Override
    protected void registerLootTable(@Nonnull Block block, @Nonnull LootTable.Builder table) {
        //Overwrite the core register method to add to our list of known blocks
        super.registerLootTable(block, table);
        knownBlocks.add(block);
    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }

    protected void skip(IBlockProvider... blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            toSkip.add(blockProvider.getBlock());
        }
    }

    protected boolean skipBlock(Block block) {
        //Skip any blocks that we already registered a table for or have marked to skip
        return knownBlocks.contains(block) || toSkip.contains(block);
    }

    protected static LootTable.Builder droppingWithFortuneOrRandomly(Block block, IItemProvider item, IRandomRange range) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item.asItem())
                .acceptFunction(SetCount.builder(range))
                .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))
        ));
    }

    //IBlockProvider versions of BlockLootTable methods, modified to support varargs
    protected void registerDropSelfLootTable(List<IBlockProvider> blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            Block block = blockProvider.getBlock();
            if (!skipBlock(block)) {
                registerDropSelfLootTable(block);
            }
        }
    }

    protected void registerLootTable(Function<Block, Builder> factory, IBlockProvider... blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            registerLootTable(blockProvider.getBlock(), factory);
        }
    }

    protected void registerDropSelfWithContentsLootTable(List<IBlockProvider> blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            Block block = blockProvider.getBlock();
            if (skipBlock(block)) {
                continue;
            }
            CopyNbt.Builder nbtBuilder = CopyNbt.builder(Source.BLOCK_ENTITY);
            boolean hasData = false;
            @Nullable
            TileEntity tile = null;
            if (block instanceof IHasTileEntity) {
                tile = ((IHasTileEntity<?>) block).getTileType().create();
            }
            if (tile instanceof ISustainedData) {
                Set<Entry<String, String>> remapEntries = ((ISustainedData) tile).getTileDataRemap().entrySet();
                for (Entry<String, String> remapEntry : remapEntries) {
                    nbtBuilder.replaceOperation(remapEntry.getKey(), "titanRealmsData" + "." + remapEntry.getValue());
                }
                if (!remapEntries.isEmpty()) {
                    hasData = true;
                }
            }

            if (!hasData) {
                //To keep the json as clean as possible don't bother even registering a blank accept function if we have no
                // persistent data that we want to copy. Also log a warning so that we don't have to attempt to check against
                // that block
                registerDropSelfLootTable(block);
            } else {
                registerLootTable(block, LootTable.builder().addLootPool(withSurvivesExplosion(block, LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(block).acceptFunction(nbtBuilder))
                )));
            }
        }
    }

    /**
     * Like vanilla's droppingSlab except with a named pool
     */
    protected static LootTable.Builder droppingSlab(Block slab) {
        return LootTable.builder().addLootPool(LootPool.builder()
                .name("main")
                .rolls(ConstantRange.of(1))
                .addEntry(withExplosionDecay(slab, ItemLootEntry.builder(slab)
                                .acceptFunction(SetCount.builder(ConstantRange.of(2))
                                        .acceptCondition(BlockStateProperty.builder(slab)
                                                .fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE)))
                                )
                        )
                )
        );
    }

    /**
     * Like vanilla's registerDropping except with a named pool
     */
    @Override
    public void registerDropping(@Nonnull Block block, @Nonnull IItemProvider drop) {
        registerLootTable(block, dropping(drop));
    }

    /**
     * Like vanilla's dropping except with a named pool
     */
    protected static LootTable.Builder dropping(IItemProvider item) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(item, LootPool.builder()
                .name("main")
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(item))
        ));
    }

    /**
     * Like vanilla's droppingWithSilkTouchOrRandomly except with a named pool
     */
    protected static LootTable.Builder droppingWithSilkTouchOrRandomly(@Nonnull Block block, @Nonnull IItemProvider item, @Nonnull IRandomRange range) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item).acceptFunction(SetCount.builder(range))));
    }

    /**
     * Like vanilla's droppingWithSilkTouch except with a named pool
     */
    protected static LootTable.Builder droppingWithSilkTouch(@Nonnull Block block, @Nonnull LootEntry.Builder<?> builder) {
        return dropping(block, SILK_TOUCH, builder);
    }

    /**
     * Like vanilla's dropping except with a named pool
     */
    protected static LootTable.Builder dropping(@Nonnull Block block, @Nonnull ILootCondition.IBuilder conditionBuilder, @Nonnull LootEntry.Builder<?> entry) {
        return LootTable.builder().addLootPool(LootPool.builder()
                .name("main")
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(block)
                        .acceptCondition(conditionBuilder)
                        .alternatively(entry)
                )
        );
    }
}
