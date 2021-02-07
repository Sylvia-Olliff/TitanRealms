package sylvantus.titanrealms.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.datagen.client.lang.TitanRealmsLanguageProvider;
import sylvantus.titanrealms.datagen.client.model.TitanRealmsItemModelProvider;
import sylvantus.titanrealms.datagen.client.sound.TitanRealmsSoundProvider;
import sylvantus.titanrealms.datagen.client.state.TitanRealmsBlockStateProvider;

@EventBusSubscriber(modid = TitanRealms.MODID, bus = Bus.MOD)
public class TitanRealmsDataGenerator {

    private TitanRealmsDataGenerator() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if (event.includeClient()) {
            gen.addProvider(new TitanRealmsLanguageProvider(gen));
            gen.addProvider(new TitanRealmsSoundProvider(gen, existingFileHelper));

            TitanRealmsItemModelProvider itemModelProvider = new TitanRealmsItemModelProvider(gen, existingFileHelper);
            gen.addProvider(itemModelProvider);
            gen.addProvider(new TitanRealmsBlockStateProvider(gen, itemModelProvider.existingFileHelper));
        }
        if (event.includeServer()) {
            //Server side data generators
//            gen.addProvider(new TitanRealmsTagProvider(gen, existingFileHelper));
//            gen.addProvider(new TitanRealmsLootProvider(gen));
//            gen.addProvider(new TitanRealmsRecipeProvider(gen));
        }
    }
}
