package sylvantus.titanrealms.core.util.helpers;

import net.minecraft.util.ResourceLocation;
import sylvantus.titanrealms.TitanRealms;

public class ResourceHelper {

    public static ResourceLocation name(String name) {
        return new ResourceLocation(TitanRealms.MODID, name);
    }
}
