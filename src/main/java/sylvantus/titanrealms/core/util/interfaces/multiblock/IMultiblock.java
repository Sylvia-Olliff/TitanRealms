package sylvantus.titanrealms.core.util.interfaces.multiblock;

import sylvantus.titanrealms.core.lib.multiblock.*;

import java.util.UUID;

public interface IMultiblock<T extends MultiblockData> extends IMultiblockBase {

    T createMultiblock();

    @SuppressWarnings("unchecked cast")
    default T getMultiblock() {
        //Basically the same as getMultiblockData(getManager()) except we skip over validating the manager is itself
        MultiblockData data = getStructure().getMultiblockData();
        if (data != null && data.isFormed()) {
            return (T) data;
        }
        return getDefaultData();
    }

    @Override
    T getDefaultData();

    MultiblockManager<T> getManager();

    UUID getCacheID();

    MultiblockCache<T> getCache();

    void resetCache();

    void setCache(MultiblockCache<T> cache);

    boolean isMaster();

    boolean canBeMaster();

    Structure getStructure();

    void setStructure(Structure structure);

    @Override
    default void setStructure(MultiblockManager<?> manager, Structure structure) {
        if (manager == getManager()) {
            setStructure(structure);
        }
    }

    @Override
    default Structure getStructure(MultiblockManager<?> manager) {
        if (manager == getManager()) {
            return getStructure();
        }
        return null;
    }

    @Override
    default boolean hasStructure(Structure structure) {
        return getStructure() == structure;
    }

    default boolean hasCache() {
        return getCache() != null;
    }

    default FormationProtocol<T> createFormationProtocol() {
        return new FormationProtocol<>(this, getStructure());
    }
}