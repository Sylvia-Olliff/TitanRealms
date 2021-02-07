package sylvantus.titanrealms.common.tiles.prefabs;

import mekanism.api.NBTConstants;
import mekanism.api.providers.IBlockProvider;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.util.NBTUtils;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TileEntityInternalMultiblock extends TileEntityTitanRealms {

    protected UUID multiblockUUID;

    public TileEntityInternalMultiblock(IBlockProvider blockProvider) { super(blockProvider); }

    public void setMultiblock(UUID id) { multiblockUUID = id; }

    public UUID getMultiblock() { return multiblockUUID; }

    @Nonnull
    @Override
    public CompoundNBT getReducedUpdateTag() {
        CompoundNBT updateTag = super.getReducedUpdateTag();
        if (multiblockUUID != null) {
            updateTag.putUniqueId(NBTConstants.INVENTORY_ID, multiblockUUID);
        }
        return updateTag;
    }

    @Override
    public void handleUpdateTag(BlockState state, @Nonnull CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        NBTUtils.setUUIDIfPresentElse(tag, NBTConstants.INVENTORY_ID, this::setMultiblock, () -> multiblockUUID = null);
    }
}
