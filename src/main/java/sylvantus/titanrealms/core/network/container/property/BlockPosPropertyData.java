package sylvantus.titanrealms.core.network.container.property;

import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;

public class BlockPosPropertyData extends PropertyData {

    @Nullable
    private final BlockPos value;

    public BlockPosPropertyData(short property, @Nullable BlockPos value) {
        super(PropertyType.BLOCK_POS, property);
        this.value = value;
    }

    @Override
    public void handleWindowProperty(TitanRealmsContainer container) {
        container.handleWindowProperty(getProperty(), value);
    }

    @Override
    public void writeToPacket(PacketBuffer buffer) {
        super.writeToPacket(buffer);
        if (value == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeBlockPos(value);
        }
    }
}