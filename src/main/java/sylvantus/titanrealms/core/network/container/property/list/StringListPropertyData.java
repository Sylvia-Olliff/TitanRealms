package sylvantus.titanrealms.core.network.container.property.list;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.network.PacketBuffer;
import sylvantus.titanrealms.core.network.BasePacketHandler;

public class StringListPropertyData extends ListPropertyData<String> {

    public StringListPropertyData(short property, @Nonnull List<String> values) {
        super(property, ListType.STRING, values);
    }

    public static StringListPropertyData read(short property, int elements, PacketBuffer buffer) {
        List<String> values = new ArrayList<>(elements);
        for (int i = 0; i < elements; i++) {
            values.add(BasePacketHandler.readString(buffer));
        }
        return new StringListPropertyData(property, values);
    }


    @Override
    protected void writeListElements(PacketBuffer buffer) {
        for (String value : values) {
            buffer.writeString(value);
        }
    }
}