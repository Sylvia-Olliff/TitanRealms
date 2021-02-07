package sylvantus.titanrealms.core.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.client.sound.SoundHandler;
import sylvantus.titanrealms.core.network.PacketResetPlayerClient;

import java.util.UUID;

public class PlayerState {

    private IWorld world;

    public void clear(boolean isRemote) {
        // TODO: clear active tracking of gear
        if (isRemote) {
            SoundHandler.clearPlayerSounds();
        }
    }

    public void clearPlayer(UUID uuid, boolean isRemote) {

        if (isRemote) {
            SoundHandler.clearPlayerSounds(uuid);
        } else {
            TitanRealms.packetHandler.sendToAll(new PacketResetPlayerClient(uuid));
        }
    }

    public void clearPlayerServerSideOnly(UUID uuid) {
        // TODO: only clear local tracking
    }

    public void reapplyServerSideOnly(PlayerEntity player) {
        // TODO: reapply any tracking that was dropped due to dim change
    }

    public void init(IWorld world) {
        this.world = world;
    }
}
