package sylvantus.titanrealms.core.network.container.property;

import net.minecraft.network.PacketBuffer;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;
import sylvantus.titanrealms.core.network.container.PacketUpdateContainer;

public abstract class PropertyData {

    private final PropertyType type;
    private final short property;

    protected PropertyData(PropertyType type, short property) {
        this.type = type;
        this.property = property;
    }

    public PropertyType getType() {
        return type;
    }

    public short getProperty() {
        return property;
    }

    public PacketUpdateContainer getSinglePacket(short windowId) {
        return new PacketUpdateContainer(windowId, property, this);
    }

    public abstract void handleWindowProperty(TitanRealmsContainer container);

    public void writeToPacket(PacketBuffer buffer) {
        buffer.writeEnumValue(type);
        buffer.writeShort(property);
    }

    public static PropertyData fromBuffer(PacketBuffer buffer) {
        PropertyType type = buffer.readEnumValue(PropertyType.class);
        short property = buffer.readShort();
        return type.createData(property, buffer);
    }
}