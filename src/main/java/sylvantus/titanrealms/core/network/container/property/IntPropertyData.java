package sylvantus.titanrealms.core.network.container.property;

import net.minecraft.network.PacketBuffer;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;

public class IntPropertyData extends PropertyData {

    private final int value;

    public IntPropertyData(short property, int value) {
        super(PropertyType.INT, property);
        this.value = value;
    }

    @Override
    public void handleWindowProperty(TitanRealmsContainer container) {
        container.handleWindowProperty(getProperty(), value);
    }

    @Override
    public void writeToPacket(PacketBuffer buffer) {
        super.writeToPacket(buffer);
        buffer.writeVarInt(value);
    }
}