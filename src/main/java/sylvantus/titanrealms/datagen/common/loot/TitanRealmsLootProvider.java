package sylvantus.titanrealms.datagen.common.loot;

import net.minecraft.data.DataGenerator;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.datagen.common.loot.table.BaseBlockLootTables;
import sylvantus.titanrealms.datagen.common.loot.table.TitanRealmsBlockLootTables;

public class TitanRealmsLootProvider extends BaseLootProvider {

    public TitanRealmsLootProvider(DataGenerator gen) {
        super(gen, TitanRealms.MODID);
    }

    @Override
    protected BaseBlockLootTables getBlockLootTable() { return new TitanRealmsBlockLootTables(); }
}
