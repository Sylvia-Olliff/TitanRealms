package sylvantus.titanrealms.client;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import sylvantus.titanrealms.client.GUI.GuiRadialSelector;
import sylvantus.titanrealms.client.render.RenderTickHandler;
import sylvantus.titanrealms.core.config.TitanRealmsConfig;

import java.util.Random;
import java.util.UUID;

public class ClientTickHandler {

    public static final Minecraft minecraft = Minecraft.getInstance();
    public static final Random rand = new Random();

    public boolean shouldReset = false;
    public static boolean firstTick = true;

    private static long lastScrollTime = -1;
    private static double scrollDelta;

    // TODO: Add static boolean methods for tracking gear the player has from Titan Realms

    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        if (event.phase == Phase.START) {
            tickStart();
        }
    }

    public void tickStart() {
        TitanRealmsClient.ticksPassed++;

        if (firstTick && minecraft.world != null) {
            TitanRealmsClient.launchClient();
            firstTick = false;
        }

        if (minecraft.world != null) {
            shouldReset = true;
        } else if (shouldReset) {
            TitanRealmsClient.reset();
            shouldReset = false;
        }

        if (minecraft.world != null && minecraft.player != null && !minecraft.isGamePaused()) {
            if (minecraft.world.getGameTime() - lastScrollTime > 20) {
                scrollDelta = 0;
            }

            UUID playerUUID = minecraft.player.getUniqueID();
            // Update player's state for various items; this also automatically notifies server if something changed and
            // kicks off sounds as necessary
            // TODO: Set player state trackers based on this class's tracking booleans
        }

        if (TitanRealmsConfig.client.enablePlayerSounds.get()) {
            // TODO: Retrieve and play any relevant sounds
        }
    }

    @SubscribeEvent
    public void onMouseEvent(InputEvent.MouseScrollEvent event) {
        /**
         *  EXAMPLE: Captures mouse scroll wheel outside of a GUI screen
         */
    }

    @SubscribeEvent
    public void onGuiMouseEvent(GuiScreenEvent.MouseScrollEvent.Pre event) {
        if (event.getGui() instanceof GuiRadialSelector) {
            //handleModeScroll(event, event.getScrollDelta());
        }
    }

//    private void handleModeScroll(Event event, double delta) {
//        if (delta != 0 && IModeItem.isModeItem(minecraft.player, EquipmentSlotType.MAINHAND)) {
//            lastScrollTime = minecraft.world.getGameTime();
//            scrollDelta += delta;
//            int shift = (int) scrollDelta;
//            scrollDelta %= 1;
//            if (shift != 0) {
//                RenderTickHandler.modeSwitchTimer = 100;
//                Mekanism.packetHandler.sendToServer(new PacketModeChange(EquipmentSlotType.MAINHAND, shift));
//            }
//            event.setCanceled(true);
//        }
//    }


}

