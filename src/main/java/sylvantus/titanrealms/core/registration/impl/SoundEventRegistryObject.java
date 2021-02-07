package sylvantus.titanrealms.core.registration.impl;

import mekanism.api.text.ILangEntry;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraftforge.fml.RegistryObject;
import sylvantus.titanrealms.core.registration.WrappedRegistryObject;

import javax.annotation.Nonnull;

public class SoundEventRegistryObject<SOUND extends SoundEvent> extends WrappedRegistryObject<SOUND> implements ILangEntry {

    private final String translationKey;

    public SoundEventRegistryObject(RegistryObject<SOUND> registryObject) {
        super(registryObject);
        translationKey = Util.makeTranslationKey("sound_event", this.registryObject.getId());
    }

    @Nonnull
    public SOUND getSoundEvent() {
        return get();
    }

    @Override
    public String getTranslationKey() {
        return translationKey;
    }
}
