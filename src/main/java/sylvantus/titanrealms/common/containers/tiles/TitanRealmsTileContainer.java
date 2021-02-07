package sylvantus.titanrealms.common.containers.tiles;

import mekanism.api.inventory.IInventorySlot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.registration.impl.ContainerTypeRegistryObject;
import sylvantus.titanrealms.core.util.WorldUtils;
import sylvantus.titanrealms.core.util.interfaces.containers.IEmptyContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TitanRealmsTileContainer<TILE extends TileEntityTitanRealms> extends TitanRealmsContainer {

    @Nullable
    protected final TILE tile;

    public TitanRealmsTileContainer(ContainerTypeRegistryObject<?> type, int id, @Nullable PlayerInventory inv, @Nullable TILE tile) {
        super(type, id, inv);
        this.tile = tile;
        addContainerTrackers();
        addSlotsAndOpen();
    }

    protected void addContainerTrackers() {
        if (tile != null) {
            tile.addContainerTrackers(this);
        }
    }

    public TILE getTileEntity() {
        return tile;
    }

    @Override
    protected void openInventory(@Nonnull PlayerInventory inv) {
        if (tile != null) {
            tile.open(inv.player);
        }
    }

    @Override
    protected void closeInventory(PlayerEntity player) {
        if (tile != null) {
            tile.close(player);
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity player) {
        if (tile == null) {
            return true;
        }
        //prevent Containers from remaining valid after the chunk has unloaded;
        return tile.hasGui() && !tile.isRemoved() && WorldUtils.isBlockLoaded(tile.getWorld(), tile.getPos());
    }

    @Override
    protected void addSlots() {
        super.addSlots();
        if (this instanceof IEmptyContainer) {
            //Don't include the inventory slots
            return;
        }
        if (tile != null && tile.hasInventory()) {
            //Get all the inventory slots the tile has
            List<IInventorySlot> inventorySlots = tile.getInventorySlots(null);
            for (IInventorySlot inventorySlot : inventorySlots) {
                Slot containerSlot = inventorySlot.createContainerSlot();
                if (containerSlot != null) {
                    addSlot(containerSlot);
                }
            }
        }
    }

    public static <TILE extends TileEntity> TILE getTileFromBuf(PacketBuffer buf, Class<TILE> type) {
        if (buf == null) {
            return null;
        }
        return DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> WorldUtils.getTileEntity(type, Minecraft.getInstance().world, buf.readBlockPos()));
    }
}
