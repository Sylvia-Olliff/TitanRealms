package sylvantus.titanrealms.core.network;

import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import sylvantus.titanrealms.common.recipes.TitanRealmsRecipeType;

public class PacketClearRecipeCache {

    public static void handle(PacketClearRecipeCache message, Supplier<Context> context) {
        context.get().enqueueWork(TitanRealmsRecipeType::clearCache);
        context.get().setPacketHandled(true);
    }

    public static void encode(PacketClearRecipeCache pkt, PacketBuffer buf) {
    }

    public static PacketClearRecipeCache decode(PacketBuffer buf) {
        return new PacketClearRecipeCache();
    }
}