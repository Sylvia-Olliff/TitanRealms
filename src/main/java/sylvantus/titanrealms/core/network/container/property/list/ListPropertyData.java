package sylvantus.titanrealms.core.network.container.property.list;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.network.PacketBuffer;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;
import sylvantus.titanrealms.core.network.container.property.PropertyData;
import sylvantus.titanrealms.core.network.container.property.PropertyType;

public abstract class ListPropertyData<TYPE> extends PropertyData {

    @Nonnull
    protected final List<TYPE> values;
    private final ListType listType;

    public ListPropertyData(short property, ListType listType, @Nonnull List<TYPE> values) {
        super(PropertyType.LIST, property);
        this.listType = listType;
        this.values = values;
    }

    public static <TYPE> ListPropertyData<TYPE> readList(short property, PacketBuffer buffer) {
        ListType listType = buffer.readEnumValue(ListType.class);
        int elements = buffer.readVarInt();
        switch (listType) {
            case STRING:
                return (ListPropertyData<TYPE>) StringListPropertyData.read(property, elements, buffer);
            default:
                TitanRealms.LOGGER.error("Unrecognized list type received: {}", listType);
                return null;
        }
    }

    @Override
    public void handleWindowProperty(TitanRealmsContainer container) {
        container.handleWindowProperty(getProperty(), values);
    }

    @Override
    public void writeToPacket(PacketBuffer buffer) {
        super.writeToPacket(buffer);
        buffer.writeEnumValue(listType);
        buffer.writeVarInt(values.size());
        writeListElements(buffer);
    }

    protected abstract void writeListElements(PacketBuffer buffer);
}