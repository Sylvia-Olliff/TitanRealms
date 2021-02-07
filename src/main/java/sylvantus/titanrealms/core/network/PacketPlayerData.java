package sylvantus.titanrealms.core.network;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketPlayerData {

    private final UUID uuid;

    public PacketPlayerData(UUID uuid) {
        this.uuid = uuid;
    }

//    private PacketPlayerData(UUID uuid) {
//        this.uuid = uuid;
//    }

    public static void handle(PacketPlayerData message, Supplier<Context> context) {
        Context ctx = context.get();
        ctx.enqueueWork(() -> {
//            Mekanism.playerState.setFlamethrowerState(message.uuid, message.activeFlamethrower, false);
//            Mekanism.playerState.setJetpackState(message.uuid, message.activeJetpack, false);
//            Mekanism.playerState.setScubaMaskState(message.uuid, message.activeScubaMask, false);
//            Mekanism.playerState.setGravitationalModulationState(message.uuid, message.activeModulator, false);
        });
        ctx.setPacketHandled(true);
    }

    public static void encode(PacketPlayerData pkt, PacketBuffer buf) {
        buf.writeUniqueId(pkt.uuid);
//        buf.writeBoolean(pkt.activeFlamethrower);
//        buf.writeBoolean(pkt.activeJetpack);
//        buf.writeBoolean(pkt.activeScubaMask);
//        buf.writeBoolean(pkt.activeModulator);
    }

    public static PacketPlayerData decode(PacketBuffer buf) {
        return new PacketPlayerData(buf.readUniqueId());
    }
}