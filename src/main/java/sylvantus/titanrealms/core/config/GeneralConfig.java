package sylvantus.titanrealms.core.config;

import mekanism.api.math.FloatingLong;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;
import sylvantus.titanrealms.core.config.value.CachedBooleanValue;
import sylvantus.titanrealms.core.config.value.CachedFloatValue;
import sylvantus.titanrealms.core.config.value.CachedFloatingLongValue;
import sylvantus.titanrealms.core.config.value.CachedIntValue;

public class GeneralConfig extends BaseTitanRealmsConfig {

    private static final String INFUSION_CATEGORY = "infusion";
    private static final String INVASION_CATEGORY = "invasion";

    private final ForgeConfigSpec configSpec;

    // General settings
    public final CachedIntValue blockDeactivationDelay;

    // infusion settings
    public final CachedBooleanValue spreadInfusion;
    public final CachedFloatValue spreadRate;
    public final CachedFloatValue balancedMin;
    public final CachedFloatingLongValue infusionMax;

    // invasion settings
    public final CachedBooleanValue invade;
    public final CachedBooleanValue invadeAesir;
    public final CachedBooleanValue flipInfusion;
    public final CachedFloatValue invasionRate;

    GeneralConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General Config. This config is synced from server to client.").push("general");

        blockDeactivationDelay = CachedIntValue.wrap(this, builder.comment("How many ticks must pass until a block's active state is synced with the client, if it has been rapidly changing")
                .define("blockDeactivationDelay", 60));

        builder.comment("Infusion Settings").push(INFUSION_CATEGORY);
        spreadInfusion = CachedBooleanValue.wrap(this, builder.comment("Disable this to prevent infusion levels from spreading out")
                .define("spreadInfusion", true));
        spreadRate = CachedFloatValue.wrap(this, builder.comment("Rate at which infusion levels spread. Measured as a percentage chance per 5 ticks")
                .define("spreadRate", 0.05));
        balancedMin = CachedFloatValue.wrap(this, builder.comment("Minimum difference between neighboring infusion amounts to be considered in balance")
                .defineInRange("blancedMin", 300.0, 0, 50000));
        infusionMax = CachedFloatingLongValue.define(this, builder, "Maximum single block infusion amount. Don't set this too high or may cause infinite spread",
                "infusionMax", FloatingLong.createConst(150000), CachedFloatingLongValue.POSITIVE);
        builder.pop();

        builder.comment("Invasion Settings").push(INVASION_CATEGORY);
        invade = CachedBooleanValue.wrap(this, builder.comment("Disable to prevent all invasions. NOTE: Will not disable warbands")
                .define("invade", true));
        invadeAesir = CachedBooleanValue.wrap(this, builder.comment("Disable to prevent Hel from invading Aesir")
                .define("invadeAesir", true));
        flipInfusion = CachedBooleanValue.wrap(this, builder.comment("Disable to prevent invaders from flipping infusion types")
                .define("flipInfusion", true));
        invasionRate = CachedFloatValue.wrap(this, builder.comment("Starting rate at which invasions can occur. Measured as a percentage chance per 2 min period.")
                .defineInRange("invasionRate", 0.08, 0.01, 1));
        builder.pop();

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() { return "general"; }

    @Override
    public ForgeConfigSpec getConfigSpec() { return configSpec; }

    @Override
    public Type getConfigType() { return Type.SERVER; }
}
