package sylvantus.titanrealms.common.blocks.blocktypes;

import mekanism.api.text.ILangEntry;
import mekanism.api.text.TextComponentUtil;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.blocks.attributes.AttributeGui;
import sylvantus.titanrealms.common.blocks.attributes.AttributeSound;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;
import sylvantus.titanrealms.common.containers.providers.ContainerProvider;
import sylvantus.titanrealms.common.containers.tiles.EmptyTileContainer;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.registration.impl.ContainerTypeRegistryObject;
import sylvantus.titanrealms.core.registration.impl.SoundEventRegistryObject;
import sylvantus.titanrealms.core.registration.impl.TileEntityTypeRegistryObject;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute.ITileAttribute;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockTypeTile<TILE extends TileEntityTitanRealms> extends BlockType {

    private final Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar;

    public BlockTypeTile(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar, ILangEntry description) {
        super(description);
        this.tileEntityRegistrar = tileEntityRegistrar;
    }

    public TileEntityType<TILE> getTileType() {
        return tileEntityRegistrar.get().getTileEntityType();
    }

    public static class BlockTileBuilder<BLOCK extends BlockTypeTile<TILE>, TILE extends TileEntityTitanRealms, T extends BlockTileBuilder<BLOCK, TILE, T>> extends BlockTypeBuilder<BLOCK, T> {

        protected BlockTileBuilder(BLOCK holder) {
            super(holder);
        }

        public static <TILE extends TileEntityTitanRealms> BlockTileBuilder<BlockTypeTile<TILE>, TILE, ?> createBlock(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar, ILangEntry description) {
            return new BlockTileBuilder<>(new BlockTypeTile<>(tileEntityRegistrar, description));
        }

        public T withSound(SoundEventRegistryObject<SoundEvent> soundRegistrar) {
            return with(new AttributeSound(soundRegistrar));
        }

        public T withGui(Supplier<ContainerTypeRegistryObject<? extends TitanRealmsContainer>> containerRegistrar) {
            return with(new AttributeGui(containerRegistrar));
        }

//        public T withEnergyConfig(FloatingLongSupplier energyUsage, FloatingLongSupplier energyStorage) {
//            return with(new AttributeEnergy(energyUsage, energyStorage));
//        }
//
//        public T withEnergyConfig(FloatingLongSupplier energyStorage) {
//            return with(new AttributeEnergy(null, energyStorage));
//        }

        @SafeVarargs
        public final T with(ITileAttribute<TILE>... attrs) {
            holder.add(attrs);
            return getThis();
        }

        public T withNamedContainerProvider(Function<TileEntityTitanRealms, INamedContainerProvider> customContainerSupplier) {
            if (!holder.has(AttributeGui.class)) {
                TitanRealms.LOGGER.error("Attempted to set a custom container on a block type without a GUI attribute.");
            }
            holder.get(AttributeGui.class).setCustomContainer(customContainerSupplier);
            return getThis();
        }

        public T withCustomContainerProvider(Function<TileEntityTitanRealms, IContainerProvider> providerFunction) {
            return withNamedContainerProvider(tile -> new ContainerProvider(TextComponentUtil.translate(tile.getBlockType().getTranslationKey()), providerFunction.apply(tile)));
        }

        public T withEmptyContainer(ContainerTypeRegistryObject<?> container) {
            return withCustomContainerProvider(tile -> ((i, inv, player) -> new EmptyTileContainer<>(container, i, inv, tile)));
        }

//        public T withSupportedUpgrades(Set<Upgrade> upgrades) {
//            holder.add(new AttributeUpgradeSupport(upgrades));
//            return getThis();
//        }
    }
}
