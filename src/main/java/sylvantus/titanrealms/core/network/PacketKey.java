package sylvantus.titanrealms.core.network;

import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import sylvantus.titanrealms.TitanRealms;

public class PacketKey {

    private final int key;
    private final boolean add;

    public PacketKey(int key, boolean add) {
        this.key = key;
        this.add = add;
    }

    public static void handle(PacketKey message, Supplier<Context> context) {
        Context ctx = context.get();
        ctx.enqueueWork(() -> {
            PlayerEntity player = ctx.getSender();
            if (player == null) {
                return;
            }
            if (message.add) {
                TitanRealms.keyMap.add(player.getUniqueID(), message.key);
            } else {
                TitanRealms.keyMap.remove(player.getUniqueID(), message.key);
            }
        });
        ctx.setPacketHandled(true);
    }

    public static void encode(PacketKey pkt, PacketBuffer buf) {
        buf.writeVarInt(pkt.key);
        buf.writeBoolean(pkt.add);
    }

    public static PacketKey decode(PacketBuffer buf) {
        return new PacketKey(buf.readVarInt(), buf.readBoolean());
    }
}