package sylvantus.titanrealms.client.model;

import net.minecraftforge.client.event.ModelBakeEvent;

import java.util.HashSet;
import java.util.Set;

public class TitanRealmsModelCache extends BaseModelCache {

    public static final TitanRealmsModelCache INSTANCE = new TitanRealmsModelCache();
    private final Set<Runnable> callbacks = new HashSet<>();

    // TODO: register OBJ models here

    public TitanRealmsModelCache() {

    }

    @Override
    public void onBake(ModelBakeEvent event) {
        super.onBake(event);
        callbacks.forEach(Runnable::run);
    }

    public void reloadCallback(Runnable callback) {
        callbacks.add(callback);
    }
}
