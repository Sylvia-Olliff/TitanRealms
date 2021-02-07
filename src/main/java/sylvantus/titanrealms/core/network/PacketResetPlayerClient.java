package sylvantus.titanrealms.core.network;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import sylvantus.titanrealms.TitanRealms;

public class PacketResetPlayerClient {

    private final UUID uuid;

    public PacketResetPlayerClient(UUID uuid) {
        this.uuid = uuid;
    }

    public static void handle(PacketResetPlayerClient message, Supplier<Context> context) {
        Context ctx = context.get();
        ctx.enqueueWork(() -> TitanRealms.playerState.clearPlayer(message.uuid, true));
        ctx.setPacketHandled(true);
    }

    public static void encode(PacketResetPlayerClient pkt, PacketBuffer buf) {
        buf.writeUniqueId(pkt.uuid);
    }

    public static PacketResetPlayerClient decode(PacketBuffer buf) {
        return new PacketResetPlayerClient(buf.readUniqueId());
    }
}
