package sylvantus.titanrealms.core.registries;

import net.minecraft.util.SoundEvent;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.registration.impl.SoundEventDeferredRegister;
import sylvantus.titanrealms.core.registration.impl.SoundEventRegistryObject;

public final class TitanRealmsSounds {

    private TitanRealmsSounds() {}

    public static final SoundEventDeferredRegister SOUND_EVENTS = new SoundEventDeferredRegister(TitanRealms.MODID);

    // TODO: Rename to something more appropriate. This is primarily here as an example.
    public static final SoundEventRegistryObject<SoundEvent> BEEP = SOUND_EVENTS.register("gui.digital_beep");

}
