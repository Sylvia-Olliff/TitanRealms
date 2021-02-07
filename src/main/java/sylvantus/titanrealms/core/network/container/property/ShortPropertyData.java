package sylvantus.titanrealms.core.network.container.property;

import net.minecraft.network.PacketBuffer;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;

public class ShortPropertyData extends PropertyData {

    private final short value;

    public ShortPropertyData(short property, short value) {
        super(PropertyType.SHORT, property);
        this.value = value;
    }

    @Override
    public void handleWindowProperty(TitanRealmsContainer container) {
        container.handleWindowProperty(getProperty(), value);
    }

    @Override
    public void writeToPacket(PacketBuffer buffer) {
        super.writeToPacket(buffer);
        buffer.writeShort(value);
    }
}