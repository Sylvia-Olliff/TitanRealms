package sylvantus.titanrealms.datagen.common.loot.table;

import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;

public class TitanRealmsBlockLootTables extends BaseBlockLootTables {

    @Override
    protected void addTables() {
        // skip(); // Add blocks that should not drop

        // Example of registering loot tables for silk touch and fortune
        // registerLootTable(block -> droppingWithSilkTouchOrRandomly(block, MekanismItems.SALT, ConstantRange.of(4)), MekanismBlocks.SALT_BLOCK);
        // registerLootTable(block -> droppingWithFortuneOrRandomly(block, MekanismItems.FLUORITE_GEM, RandomValueRange.of(2, 4)), MekanismBlocks.ORES.get(OreType.FLUORITE));

        //Register the remaining blocks as dropping themselves with any contents they may have stored
        registerDropSelfWithContentsLootTable(TitanRealmsBlocks.BLOCKS.getAllBlocks());
    }
}
