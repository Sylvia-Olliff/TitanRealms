package sylvantus.titanrealms.client.util;

import mekanism.api.providers.IBlockProvider;
import mekanism.api.providers.IItemProvider;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.particle.ParticleManager.IParticleMetaFactory;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import sylvantus.titanrealms.client.render.TitanRealmsRenderer;
import sylvantus.titanrealms.core.registration.impl.ContainerTypeRegistryObject;
import sylvantus.titanrealms.core.registration.impl.EntityTypeRegistryObject;
import sylvantus.titanrealms.core.registration.impl.ParticleTypeRegistryObject;
import sylvantus.titanrealms.core.registration.impl.TileEntityTypeRegistryObject;
import sylvantus.titanrealms.core.util.interfaces.blocks.IColoredBlock;

import java.util.function.Function;
import java.util.function.Predicate;

public class ClientRegistrationUtil {

    private ClientRegistrationUtil() {}

    public static <T extends Entity> void registerEntityRenderingHandler(EntityTypeRegistryObject<T> entityTypeRO, IRenderFactory<? super T> renderFactory) {
        RenderingRegistry.registerEntityRenderingHandler(entityTypeRO.getEntityType(), renderFactory);
    }

    public static synchronized <T extends TileEntity> void bindTileEntityRenderer(TileEntityTypeRegistryObject<T> tileTypeRO,
                                                                                  Function<TileEntityRendererDispatcher, TileEntityRenderer<? super T>> renderFactory) {
        ClientRegistry.bindTileEntityRenderer(tileTypeRO.getTileEntityType(), renderFactory);
    }

    @SafeVarargs
    public static synchronized <T extends TileEntity> void bindTileEntityRenderer(Function<TileEntityRendererDispatcher, TileEntityRenderer<T>> rendererFactory,
                                                                                  TileEntityTypeRegistryObject<? extends T>... tileEntityTypeROs) {
        TileEntityRenderer<T> renderer = rendererFactory.apply(TileEntityRendererDispatcher.instance);
        for (TileEntityTypeRegistryObject<? extends T> tileTypeRO : tileEntityTypeROs) {
            ClientRegistry.bindTileEntityRenderer(tileTypeRO.getTileEntityType(), constant -> renderer);
        }
    }

    public static <T extends IParticleData> void registerParticleFactory(ParticleTypeRegistryObject<T> particleTypeRO, IParticleMetaFactory<T> factory) {
        Minecraft.getInstance().particles.registerFactory(particleTypeRO.getParticleType(), factory);
    }

    public static <C extends Container, U extends Screen & IHasContainer<C>> void registerScreen(ContainerTypeRegistryObject<C> type, IScreenFactory<C, U> factory) {
        ScreenManager.registerFactory(type.getContainerType(), factory);
    }

    public static void setPropertyOverride(IItemProvider itemProvider, ResourceLocation override, IItemPropertyGetter propertyGetter) {
        ItemModelsProperties.registerProperty(itemProvider.getItem(), override, propertyGetter);
    }

    public static void registerItemColorHandler(ItemColors colors, IItemColor itemColor, IItemProvider... items) {
        for (IItemProvider itemProvider : items) {
            colors.register(itemColor, itemProvider.getItem());
        }
    }

    public static void registerBlockColorHandler(BlockColors blockColors, IBlockColor blockColor, IBlockProvider... blocks) {
        for (IBlockProvider blockProvider : blocks) {
            blockColors.register(blockColor, blockProvider.getBlock());
        }
    }

    public static void registerBlockColorHandler(BlockColors blockColors, ItemColors itemColors, IBlockColor blockColor, IItemColor itemColor, IBlockProvider... blocks) {
        for (IBlockProvider blockProvider : blocks) {
            blockColors.register(blockColor, blockProvider.getBlock());
            itemColors.register(itemColor, blockProvider.getItem());
        }
    }

    public static void registerIColoredBlockHandler(BlockColors blockColors, ItemColors itemColors, IBlockProvider... blocks) {
        ClientRegistrationUtil.registerBlockColorHandler(blockColors, itemColors, (state, world, pos, tintIndex) -> {
            Block block = state.getBlock();
            if (block instanceof IColoredBlock) {
                return TitanRealmsRenderer.getColorARGB(((IColoredBlock) block).getColor(), 1);
            }
            return -1;
        }, (stack, tintIndex) -> {
            Item item = stack.getItem();
            if (item instanceof BlockItem) {
                Block block = ((BlockItem) item).getBlock();
                if (block instanceof IColoredBlock) {
                    return TitanRealmsRenderer.getColorARGB(((IColoredBlock) block).getColor(), 1);
                }
            }
            return -1;
        }, blocks);
    }

    public static void setRenderLayer(RenderType type, IBlockProvider... blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            RenderTypeLookup.setRenderLayer(blockProvider.getBlock(), type);
        }
    }

    public static synchronized void setRenderLayer(Predicate<RenderType> predicate, IBlockProvider... blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            RenderTypeLookup.setRenderLayer(blockProvider.getBlock(), predicate);
        }
    }

//    public static void setRenderLayer(RenderType type, FluidRegistryObject<?, ?, ?, ?>... fluidROs) {
//        for (FluidRegistryObject<?, ?, ?, ?> fluidRO : fluidROs) {
//            RenderTypeLookup.setRenderLayer(fluidRO.getStillFluid(), type);
//            RenderTypeLookup.setRenderLayer(fluidRO.getFlowingFluid(), type);
//        }
//    }
//
//    public static synchronized void setRenderLayer(Predicate<RenderType> predicate, FluidRegistryObject<?, ?, ?, ?>... fluidROs) {
//        for (FluidRegistryObject<?, ?, ?, ?> fluidRO : fluidROs) {
//            RenderTypeLookup.setRenderLayer(fluidRO.getStillFluid(), predicate);
//            RenderTypeLookup.setRenderLayer(fluidRO.getFlowingFluid(), predicate);
//        }
//    }
}
