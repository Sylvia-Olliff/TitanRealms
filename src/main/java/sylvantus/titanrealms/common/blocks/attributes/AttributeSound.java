package sylvantus.titanrealms.common.blocks.attributes;

import net.minecraft.util.SoundEvent;
import sylvantus.titanrealms.core.registration.impl.SoundEventRegistryObject;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute;

import javax.annotation.Nonnull;

public class AttributeSound implements IAttribute {

    private final SoundEventRegistryObject<SoundEvent> soundRegistrar;

    public AttributeSound(SoundEventRegistryObject<SoundEvent> soundRegistrar) {
        this.soundRegistrar = soundRegistrar;
    }

    @Nonnull
    public SoundEvent getSoundEvent() {
        return soundRegistrar.getSoundEvent();
    }
}
