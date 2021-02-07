package sylvantus.titanrealms.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;
import sylvantus.titanrealms.core.TitanRealmsLang;

public class TitanRealmsKeyHandler extends TRKeyHandler {

    public static final KeyBinding detailsKey = new KeyBinding(TitanRealmsLang.KEY_DETAILS_MODE.getTranslationKey(), KeyConflictContext.GUI, InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_SHIFT, TitanRealmsLang.TITANREALMS.getTranslationKey());
    public static final KeyBinding descriptionKey = new KeyBinding(TitanRealmsLang.KEY_DESCRIPTION_MODE.getTranslationKey(), KeyConflictContext.GUI,
            KeyModifier.SHIFT, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_N, TitanRealmsLang.TITANREALMS.getTranslationKey());

    private static final Builder BINDINGS = new Builder(2)
            .addBinding(detailsKey, false)
            .addBinding(descriptionKey, false);

    public TitanRealmsKeyHandler() {
        super(BINDINGS);
        ClientRegistry.registerKeyBinding(detailsKey);
        ClientRegistry.registerKeyBinding(descriptionKey);
        MinecraftForge.EVENT_BUS.addListener(this::onTick);
    }

    private void onTick(InputEvent.KeyInputEvent event) {
        keyTick();
    }

    @Override
    public void keyDown(KeyBinding kb, boolean isRepeat) {
        // TODO: Add server notification packets for keys that update a device or machine
    }

    @Override
    public void keyUp(KeyBinding kb) {
        // TODO: add awareness around keys that matter when they are released
    }
}
