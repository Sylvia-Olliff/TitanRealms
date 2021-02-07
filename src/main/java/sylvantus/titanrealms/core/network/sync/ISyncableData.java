package sylvantus.titanrealms.core.network.sync;

import sylvantus.titanrealms.core.network.container.property.PropertyData;

public interface ISyncableData {

    DirtyType isDirty();

    //DirtyType will either be DIRTY or SIZE
    PropertyData getPropertyData(short property, DirtyType dirtyType);

    enum DirtyType {
        CLEAN,
        SIZE,
        DIRTY;

        public static DirtyType get(boolean dirty) {
            return dirty ? DIRTY : CLEAN;
        }
    }
}