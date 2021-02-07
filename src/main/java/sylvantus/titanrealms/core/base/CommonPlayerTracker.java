package sylvantus.titanrealms.core.base;

import mekanism.api.text.EnumColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.core.TitanRealmsLang;
import sylvantus.titanrealms.core.network.PacketClearRecipeCache;
import sylvantus.titanrealms.core.network.PacketPlayerData;
import sylvantus.titanrealms.core.network.PacketResetPlayerClient;

public class CommonPlayerTracker {

    private static final ITextComponent ALPHA_WARNING = TitanRealmsLang.LOG_FORMAT.translateColored(EnumColor.BRIGHT_GREEN, TitanRealmsLang.TITANREALMS, EnumColor.GRAY,
            TitanRealmsLang.ALPHA_WARNING.translate(EnumColor.AQUA, TextFormatting.UNDERLINE, new ClickEvent(Action.OPEN_URL,
                    "https://github.com/Sylvia-Olliff/TitanRealms#alpha-status"), TitanRealmsLang.ALPHA_WARNING_HERE));

    public CommonPlayerTracker() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerLoginEvent(PlayerLoggedInEvent event) {
        if (!event.getPlayer().world.isRemote()) {
            TitanRealms.packetHandler.sendTo(new PacketClearRecipeCache(), (ServerPlayerEntity) event.getPlayer());

            // TODO: Sync all relevant saved player data on login.
        }
    }

    @SubscribeEvent
    public void onPlayerLogoutEvent(PlayerLoggedOutEvent event) {
        PlayerEntity player = event.getPlayer();
        TitanRealms.playerState.clearPlayer(player.getUniqueID(), false);
        TitanRealms.playerState.clearPlayerServerSideOnly(player.getUniqueID());
    }

    @SubscribeEvent
    public void onPlayerDimChangedEvent(PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        TitanRealms.playerState.clearPlayer(player.getUniqueID(), false);
        TitanRealms.playerState.reapplyServerSideOnly(player);
    }

    @SubscribeEvent
    public void onPlayerStartTrackingEvent(StartTracking event) {
        if (event.getTarget() instanceof PlayerEntity && event.getPlayer() instanceof ServerPlayerEntity) {
            TitanRealms.packetHandler.sendTo(new PacketPlayerData(event.getTarget().getUniqueID()), (ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        // TODO: Attach any necessary Capabilities to any LivingEntities
    }

    @SubscribeEvent
    public void cloneEvent(Clone event) {
        // TODO: copy over any necessary capabilities to the clone
    }

    @SubscribeEvent
    public void respawnEvent(PlayerRespawnEvent event) {
        // TODO: Reset any values that should drain upon player death
        TitanRealms.packetHandler.sendToAll(new PacketResetPlayerClient(event.getPlayer().getUniqueID()));
    }

    @SubscribeEvent
    public void onEntitySpawn(EntityJoinWorldEvent event) {
        // TODO: This may be unnecessary but I might need it to prevent vanilla mobs from spawning in my dims.
    }
}
