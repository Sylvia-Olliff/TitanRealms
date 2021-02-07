package sylvantus.titanrealms.core.util.interfaces.containers;

import net.minecraft.item.ItemStack;
import sylvantus.titanrealms.core.lib.inventory.HashedItem;

import java.util.UUID;

public interface ISlotClickHandler {

    void onClick(IScrollableSlot slot, int button, boolean hasShiftDown, ItemStack heldItem);

    interface IScrollableSlot {

        HashedItem getItem();

        UUID getItemUUID();

        long getCount();

        String getDisplayName();

        String getModID();
    }
}
