package sylvantus.titanrealms.core.util.interfaces.tiles;

import sylvantus.titanrealms.core.config.TitanRealmsConfig;

public interface IActiveState {

    boolean getActive();

    void setActive(boolean active);

    boolean renderUpdate();

    boolean lightingUpdate();

    default int getActiveLightValue() {
        return TitanRealmsConfig.client.ambientLightingLevel.get();
    }
}
