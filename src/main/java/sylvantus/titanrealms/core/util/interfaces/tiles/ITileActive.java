package sylvantus.titanrealms.core.util.interfaces.tiles;

public interface ITileActive extends IActiveState {

    default boolean isActivatable() {
        return true;
    }

    @Override
    default boolean renderUpdate() {
        return false;
    }

    @Override
    default boolean lightingUpdate() {
        return false;
    }
}
