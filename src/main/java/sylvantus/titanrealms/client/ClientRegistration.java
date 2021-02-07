package sylvantus.titanrealms.client;

import mekanism.api.providers.IItemProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.client.model.baked.ExtensionBakedModel.LightedBakedModel;
import sylvantus.titanrealms.client.render.RenderTickHandler;
import sylvantus.titanrealms.client.sound.SoundHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = TitanRealms.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    private static final Map<ResourceLocation, CustomModelRegistryObject> customModels = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
        MinecraftForge.EVENT_BUS.register(SoundHandler.class);
        new TitanRealmsKeyHandler();

        // Register entity rendering handlers

        // Register TileEntityRenderers

        // Block render layers
        // Cutout

        // Multi-Layer blocks (requiring both sold & translucent render layers)
        /**
         * TODO: Not sure I'll be using this, but I like the idea so keeping an example
         */
//        ClientRegistrationUtil.setRenderLayer(renderType -> renderType == RenderType.getSolid() || renderType == RenderType.getTranslucent(),
//                MekanismBlocks.ISOTOPIC_CENTRIFUGE, MekanismBlocks.ANTIPROTONIC_NUCLEOSYNTHESIZER, MekanismBlocks.CHEMICAL_CRYSTALLIZER);
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        // Register Gui screens for associated TEs
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {

    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        event.getModelRegistry().replaceAll((rl, model) -> {
            CustomModelRegistryObject obj = customModels.get(new ResourceLocation(rl.getNamespace(), rl.getPath()));
            return obj == null ? model : obj.createModel(model, event);
        });
//        MekanismModelCache.INSTANCE.onBake(event);
    }

    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event) {
        EntityRendererManager entityRenderManager = Minecraft.getInstance().getRenderManager();
        //Add our own custom armor layer to the various player renderers
        for (Entry<String, PlayerRenderer> entry : entityRenderManager.getSkinMap().entrySet()) {
            addCustomArmorLayer(entry.getValue());
        }
        //Add our own custom armor layer to everything that has an armor layer
        //Note: This includes any modded mobs that have vanilla's BipedArmorLayer added to them
        for (Entry<EntityType<?>, EntityRenderer<?>> entry : entityRenderManager.renderers.entrySet()) {
            EntityRenderer<?> renderer = entry.getValue();
            if (renderer instanceof LivingRenderer) {
                addCustomArmorLayer((LivingRenderer) renderer);
            }
        }
    }

    private static <T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> void addCustomArmorLayer(LivingRenderer<T, M> renderer) {
        for (LayerRenderer<T, M> layerRenderer : new ArrayList<>(renderer.layerRenderers)) {
            //Only allow an exact match, so we don't add to modded entities that only have a modded extended armor layer
            if (layerRenderer.getClass() == BipedArmorLayer.class) {
                BipedArmorLayer<T, M, A> bipedArmorLayer = (BipedArmorLayer<T, M, A>) layerRenderer;
                //renderer.addLayer(new MekanismArmorLayer<>(renderer, bipedArmorLayer.modelLeggings, bipedArmorLayer.modelArmor));
                break;
            }
        }
    }

    public static void addCustomModel(IItemProvider provider, CustomModelRegistryObject object) {
        customModels.put(provider.getRegistryName(), object);
    }

    public static void addLitModel(IItemProvider... providers) {
        for (IItemProvider provider : providers) {
            addCustomModel(provider, (orig, evt) -> new LightedBakedModel(orig));
        }
    }

    @FunctionalInterface
    public interface CustomModelRegistryObject {

        IBakedModel createModel(IBakedModel original, ModelBakeEvent event);
    }
}
