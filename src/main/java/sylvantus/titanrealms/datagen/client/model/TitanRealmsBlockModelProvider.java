package sylvantus.titanrealms.datagen.client.model;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import sylvantus.titanrealms.TitanRealms;

public class TitanRealmsBlockModelProvider extends BaseBlockModelProvider {

    public TitanRealmsBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TitanRealms.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}