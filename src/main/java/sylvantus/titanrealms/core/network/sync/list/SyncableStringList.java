package sylvantus.titanrealms.core.network.sync.list;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import mekanism.api.annotations.NonNull;
import sylvantus.titanrealms.core.network.container.property.list.ListPropertyData;
import sylvantus.titanrealms.core.network.container.property.list.StringListPropertyData;

/**
 * Version of {@link net.minecraft.util.IntReferenceHolder} for handling string lists
 */
public class SyncableStringList extends SyncableList<String> {

    public static SyncableStringList create(Supplier<@NonNull List<String>> getter, Consumer<@NonNull List<String>> setter) {
        return new SyncableStringList(getter, setter);
    }

    private SyncableStringList(Supplier<@NonNull List<String>> getter, Consumer<@NonNull List<String>> setter) {
        super(getter, setter);
    }

    @Override
    public ListPropertyData<String> getPropertyData(short property, DirtyType dirtyType) {
        return new StringListPropertyData(property, get());
    }
}