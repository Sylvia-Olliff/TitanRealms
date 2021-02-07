package sylvantus.titanrealms.datagen.client.sound;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.registries.TitanRealmsSounds;

public class TitanRealmsSoundProvider extends BaseSoundProvider {

    public TitanRealmsSoundProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, existingFileHelper, TitanRealms.MODID);
    }

    @Override
    protected void addSoundEvents() {
        addTileSoundEvents();
        addItemSoundEvents();
        addGuiSoundEvents();
    }

    private void addTileSoundEvents() {
        String basePath = "tile/";
    }

    private void addItemSoundEvents() {
        String basePath = "item/";

    }

    private void addGuiSoundEvents() {
        String basePath = "gui/";
        addSoundEvent(TitanRealmsSounds.BEEP, TitanRealms.rl(basePath + "beep"));
    }
}
