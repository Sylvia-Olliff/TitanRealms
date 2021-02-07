package sylvantus.titanrealms.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.network.PacketKey;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class TitanRealmsClient {

    private TitanRealmsClient() {}

    public static final Map<UUID, String> clientUUIDMap = new Object2ObjectOpenHashMap<>();

    public static long ticksPassed = 0;

    public static void updateKey(KeyBinding key, int type) {
        boolean down = Minecraft.getInstance().currentScreen == null && key.isKeyDown();
        if (Minecraft.getInstance().player != null) {
            UUID playerUUID = Minecraft.getInstance().player.getUniqueID();
            if (down != TitanRealms.keyMap.has(playerUUID, type)) {
                TitanRealms.packetHandler.sendToServer(new PacketKey(type, down));
                TitanRealms.keyMap.update(playerUUID, type, down);
            }
        }
    }

    public static void reset() {
        clientUUIDMap.clear();

        ClientTickHandler.firstTick = true;

        TitanRealms.playerState.clear(true);
    }

    public static void launchClient() {
        // TODO: launch Client method for each gear item loaded
    }

    @Nullable
    public static World tryGetClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Nullable
    public static PlayerEntity tryGetClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
