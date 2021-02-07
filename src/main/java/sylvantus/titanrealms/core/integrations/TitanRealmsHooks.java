package sylvantus.titanrealms.core.integrations;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import sylvantus.titanrealms.core.integrations.TOP.TOPProvider;

public final class TitanRealmsHooks {

    public static final String JEI_MOD_ID = "jei";
    public static final String TOP_MOD_ID = "theoneprobe";

    public boolean JEILoaded = false;
    public boolean TOPLoaded = false;

    public void hookCommonSetup() {
        ModList modList = ModList.get();
        JEILoaded = modList.isLoaded(JEI_MOD_ID);
        TOPLoaded = modList.isLoaded(TOP_MOD_ID);
    }

    public void sendIMCMessages(InterModEnqueueEvent event) {
        if (TOPLoaded) {
            InterModComms.sendTo(TOP_MOD_ID, "getTheOneProbe", TOPProvider::new);
        }
    }
}
