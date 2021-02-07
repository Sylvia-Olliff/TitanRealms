package sylvantus.titanrealms.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;
import sylvantus.titanrealms.core.config.value.CachedBooleanValue;
import sylvantus.titanrealms.core.config.value.CachedFloatValue;
import sylvantus.titanrealms.core.config.value.CachedIntValue;

public class ClientConfig extends BaseTitanRealmsConfig {

    private static final String GUI_CATEGORY = "gui";

    private final ForgeConfigSpec configSpec;

    public final CachedBooleanValue enablePlayerSounds;
    public final CachedBooleanValue enableMachineSounds;
    public final CachedBooleanValue enableMachineEffects;
    public final CachedFloatValue baseSoundVolume;
    public final CachedBooleanValue enableAmbientLighting;
    public final CachedIntValue ambientLightingLevel;
    public final CachedIntValue terRange;

    ClientConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Client Config. This config only exists on the client").push("client");

        enablePlayerSounds = CachedBooleanValue.wrap(this, builder.comment("Play sounds for Titan's Scepters/HelForged Items/Spells/Divine Gear (all players)")
                .define("enablePlayerSounds", true));
        enableMachineSounds = CachedBooleanValue.wrap(this, builder.comment("If enabled devices and machines play their sounds while running")
                .define("enableMachineSounds", true));
        enableMachineEffects = CachedBooleanValue.wrap(this, builder.comment("Disable this to prevent animation of machine effects")
                .define("enableMachineEffects", true));
        baseSoundVolume = CachedFloatValue.wrap(this, builder.comment("Adjust Titan Realms sounds' base volume. < 1 is softer, higher is louder.")
                .defineInRange("baseSoundVolume", 1, 0, Float.MAX_VALUE));
        enableAmbientLighting = CachedBooleanValue.wrap(this, builder.comment("Should active machines and devices produce block light.")
                .define("enableAmbientLighting", true));
        ambientLightingLevel = CachedIntValue.wrap(this, builder.comment("How much light to produce if ambient lighting is enabled")
                .defineInRange("ambientLightLevel", 15, 1, 15));
        terRange = CachedIntValue.wrap(this, builder.comment("Range at which Tile Entity Renderer's added by Titan Realms can render at. Vanilla defaults the rendering range for TERs to 64 for most blocks, but uses a range of 256 for beacons and end gateways.")
                .defineInRange("terRange", 256, 1, 1_024));

        builder.comment("GUI Config").push(GUI_CATEGORY);

        builder.pop();

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "client";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.CLIENT;
    }
}
