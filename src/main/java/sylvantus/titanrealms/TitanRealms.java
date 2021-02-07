package sylvantus.titanrealms;

import mekanism.api.NBTConstants;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sylvantus.titanrealms.common.capabilities.Capabilities;
import sylvantus.titanrealms.core.CreativeTabTitanRealms;
import sylvantus.titanrealms.core.base.*;
import sylvantus.titanrealms.core.config.TitanRealmsConfig;
import sylvantus.titanrealms.core.config.TitanRealmsModConfig;
import sylvantus.titanrealms.core.integrations.TitanRealmsHooks;
import sylvantus.titanrealms.core.lib.Version;
import sylvantus.titanrealms.core.lib.multiblock.MultiblockManager;
import sylvantus.titanrealms.core.network.PacketHandler;
import sylvantus.titanrealms.core.network.sync.dynamic.SyncMapper;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;
import sylvantus.titanrealms.core.registries.TitanRealmsItems;
import sylvantus.titanrealms.core.registries.TitanRealmsSounds;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TitanRealms.MODID)
public class TitanRealms
{
    public static final String MODID = "titanrealms";
    public static final String MOD_NAME = "Titan Realms";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final PlayerState playerState = new PlayerState();

    public final Version versionNumber;

    public static TitanRealms instance;

    public static final TitanRealmsHooks hooks = new TitanRealmsHooks();

    public static final PacketHandler packetHandler = new PacketHandler();

    public static final CreativeTabTitanRealms tabTitanRealms = new CreativeTabTitanRealms();

    public static final CommonWorldTickHandler worldTickHandler = new CommonWorldTickHandler();

    public static final KeySync keyMap = new KeySync();

    private ReloadListener recipeCacheManager;

    public TitanRealms() {
        instance = this;
        TitanRealmsConfig.registerConfigs(ModLoadingContext.get());

        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.addListener(this::chunkSave);
        MinecraftForge.EVENT_BUS.addListener(this::onChunkDataLoad);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldLoad);
        MinecraftForge.EVENT_BUS.addListener(this::serverStopped);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::addReloadListenersLowest);
        MinecraftForge.EVENT_BUS.addListener(this::onVanillaTagsReload);
        MinecraftForge.EVENT_BUS.addListener(this::onCustomTagsReload);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onConfigLoad);
        modEventBus.addListener(this::imcQueue);
        TitanRealmsItems.ITEMS.register(modEventBus);
        TitanRealmsBlocks.BLOCKS.register(modEventBus);
        TitanRealmsSounds.SOUND_EVENTS.register(modEventBus);
        //Set our version number to match the mods.toml file, which matches the one in our build.gradle
        versionNumber = new Version(ModLoadingContext.get().getActiveContainer());

    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(TitanRealms.MODID, path);
    }

    private void setRecipeCacheManager(ReloadListener manager) {
        if (recipeCacheManager == null) {
            recipeCacheManager = manager;
        } else {
            LOGGER.warn("Recipe cache manager has already been set.");
        }
    }

    public ReloadListener getRecipeCacheManager() {
        return recipeCacheManager;
    }

    private void onVanillaTagsReload(TagsUpdatedEvent.VanillaTagTypes event) {
        TagCache.resetVanillaTagCaches();
    }

    private void onCustomTagsReload(TagsUpdatedEvent.CustomTagTypes event) {
        TagCache.resetCustomTagCaches();
    }

    private void addReloadListenersLowest(AddReloadListenerEvent event) {
        //Note: We register reload listeners here which we want to make sure run after CraftTweaker or any other mods that may modify recipes
        event.addListener(getRecipeCacheManager());
    }

    private void serverStopped(FMLServerStoppedEvent event) {
        playerState.clear(false);
        worldTickHandler.resetRegenChunks();
        MultiblockManager.reset();
    }

    private void imcQueue(InterModEnqueueEvent event) {
        hooks.sendIMCMessages(event);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        hooks.hookCommonSetup();
        Capabilities.registerCapabilities();
        setRecipeCacheManager(new ReloadListener());

        event.enqueueWork(() -> {
            // Register world gen features
//            GenHandler.setupWorldGenFeatures();
            SyncMapper.collectScanData();
            // Entity Attribute assignment
//            GlobalEntityTypeAttributes.put(TitanRealmsEntityTypes);
        });

        MinecraftForge.EVENT_BUS.register(new CommonPlayerTracker());
        MinecraftForge.EVENT_BUS.register(new CommonPlayerTickHandler());
        MinecraftForge.EVENT_BUS.register(TitanRealms.worldTickHandler);

        //Initialization notification
        LOGGER.info("Version {} initializing...", versionNumber);

        //Packet registrations
        packetHandler.initialize();

        //Completion notification
        LOGGER.info("Loading complete.");

        //Success message
        LOGGER.info("Mod loaded.");
    }

    private void chunkSave(ChunkDataEvent.Save event) {
        if (event.getWorld() != null && !event.getWorld().isRemote()) {
            //TODO - 1.17: Make both this and load write to the main tag instead of the level sub tag. For now we are using the level tag
            // in both spots to have proper backwards compatibility with earlier mek release versions from 1.16
            CompoundNBT levelTag = event.getData().getCompound(NBTConstants.CHUNK_DATA_LEVEL);
//            levelTag.putInt(NBTConstants.WORLD_GEN_VERSION, TitanRealmsConfig.world.userGenVersion.get());
        }
    }

    private synchronized void onChunkDataLoad(ChunkDataEvent.Load event) {

    }

    private void onConfigLoad(ModConfig.ModConfigEvent configEvent) {
        //Note: We listen to both the initial load and the reload, so as to make sure that we fix any accidentally
        // cached values from calls before the initial loading
        ModConfig config = configEvent.getConfig();
        //Make sure it is for the same modid as us
        if (config.getModId().equals(MODID) && config instanceof TitanRealmsModConfig) {
            ((TitanRealmsModConfig) config).clearCache();
        }
    }

    private void onWorldLoad(WorldEvent.Load event) {
        playerState.init(event.getWorld());
    }
}
