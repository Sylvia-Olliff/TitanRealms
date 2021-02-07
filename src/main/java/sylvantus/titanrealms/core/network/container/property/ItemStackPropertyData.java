package sylvantus.titanrealms.core.network.container.property;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;

public class ItemStackPropertyData extends PropertyData {

    @Nonnull
    private final ItemStack value;

    public ItemStackPropertyData(short property, @Nonnull ItemStack value) {
        super(PropertyType.ITEM_STACK, property);
        this.value = value;
    }

    @Override
    public void handleWindowProperty(TitanRealmsContainer container) {
        container.handleWindowProperty(getProperty(), value);
    }

    @Override
    public void writeToPacket(PacketBuffer buffer) {
        super.writeToPacket(buffer);
        buffer.writeItemStack(value);
    }
}