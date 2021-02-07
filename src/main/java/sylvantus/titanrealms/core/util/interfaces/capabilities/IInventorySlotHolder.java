package sylvantus.titanrealms.core.util.interfaces.capabilities;

import mekanism.api.inventory.IInventorySlot;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IInventorySlotHolder extends IHolder {

    @Nonnull
    List<IInventorySlot> getInventorySlots(@Nullable Direction side);
}
