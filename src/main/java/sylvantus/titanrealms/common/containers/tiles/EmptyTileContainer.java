package sylvantus.titanrealms.common.containers.tiles;

import net.minecraft.entity.player.PlayerInventory;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.registration.impl.ContainerTypeRegistryObject;
import sylvantus.titanrealms.core.util.interfaces.containers.IEmptyContainer;

import javax.annotation.Nullable;

public class EmptyTileContainer<TILE extends TileEntityTitanRealms> extends TitanRealmsTileContainer<TILE> implements IEmptyContainer {

    public EmptyTileContainer(ContainerTypeRegistryObject<?> type, int id, @Nullable PlayerInventory inv, TILE tile) {
        super(type, id, inv, tile);
    }
}
