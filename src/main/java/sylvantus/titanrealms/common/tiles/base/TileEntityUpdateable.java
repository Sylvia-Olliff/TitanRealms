package sylvantus.titanrealms.common.tiles.base;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.config.TitanRealmsConfig;
import sylvantus.titanrealms.core.util.WorldUtils;
import sylvantus.titanrealms.core.util.interfaces.tiles.ITileWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TileEntityUpdateable extends TileEntity implements ITileWrapper {

    public TileEntityUpdateable(TileEntityType<?> type) { super(type); }

    @Nonnull
    protected World getWorldNN() {
        return Objects.requireNonNull(getWorld(), "getWorldNN called before world set");
    }

    public boolean isRemote() {
        return getWorldNN().isRemote();
    }

    public void markDirtyComparator() {}

    @Override
    public void markDirty() { markDirty(true); }

    public void markDirty(boolean recheckBlockState) {
        if (world != null) {
            if (recheckBlockState) {
                cachedBlockState = world.getBlockState(pos);
            }
            WorldUtils.markChunkDirty(world, pos);
            if (!isRemote()) {
                markDirtyComparator();
            }
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), 0, getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, @Nonnull CompoundNBT tag) {
        //We don't want to do a full read from NBT so simply call the super's read method to let Forge do whatever
        // it wants, but don't treat this as if it was the full saved NBT data as not everything has to be synced to the client
        super.read(state, tag);
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return getReducedUpdateTag();
    }

    @Nonnull
    public CompoundNBT getReducedUpdateTag() {
        //Add the base update tag information
        return super.getUpdateTag();
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if (isRemote() && net.getDirection() == PacketDirection.CLIENTBOUND) {
            //Handle the update tag when we are on the client
            handleUpdatePacket(pkt.getNbtCompound());
        }
    }

    public void handleUpdatePacket(@Nonnull CompoundNBT tag) {
        handleUpdateTag(getBlockState(), tag);
    }

    public void sendUpdatePacket() {
        sendUpdatePacket(this);
    }

    public void sendUpdatePacket(TileEntity tracking) {
        if (isRemote()) {
            TitanRealms.LOGGER.warn("Update packet call requested from client side", new IllegalStateException());
        } else if (isRemoved()) {
            TitanRealms.LOGGER.warn("Update packet call requested for removed tile", new IllegalStateException());
        } else {
            // TODO: implement Packet Handler
        }
    }

    @Override
    public World getTileWorld() {
        return getWorld();
    }

    @Override
    public BlockPos getTilePos() {
        return getPos();
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return TitanRealmsConfig.client.terRange.get();
    }
}
