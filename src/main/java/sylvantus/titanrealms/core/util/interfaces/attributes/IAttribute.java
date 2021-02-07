package sylvantus.titanrealms.core.util.interfaces.attributes;

import com.google.common.collect.Lists;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import sylvantus.titanrealms.common.blocks.attributes.AttributeStateActive;
import sylvantus.titanrealms.common.blocks.attributes.AttributeStateFacing;
import sylvantus.titanrealms.common.blocks.attributes.AttributeTier;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.enums.BaseTier;
import sylvantus.titanrealms.core.util.interfaces.ITier;
import sylvantus.titanrealms.core.util.interfaces.ITypeBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Consumer;

public interface IAttribute {

    interface ITileAttribute<TILE extends TileEntityTitanRealms> extends IAttribute {}

    default void adjustProperties(AbstractBlock.Properties props) {}

    static boolean has(Block block, Class<? extends IAttribute> type) {
        return block instanceof ITypeBlock && ((ITypeBlock) block).getType().has(type);
    }

    static <T extends IAttribute> T get(Block block, Class<T> type) {
        return block instanceof ITypeBlock ? ((ITypeBlock) block).getType().get(type) : null;
    }

    static boolean has(Block block1, Block block2, Class<? extends IAttribute> type) {
        return has(block1, type) && has(block2, type);
    }

    static Collection<IAttribute> getAll(Block block) {
        return block instanceof ITypeBlock ? ((ITypeBlock) block).getType().getAll() : Lists.newArrayList();
    }

    static <T extends IAttribute> void ifHas(Block block, Class<T> type, Consumer<T> run) {
        if (block instanceof ITypeBlock) {
            T attribute = ((ITypeBlock) block).getType().get(type);
            if (attribute != null) {
                run.accept(attribute);
            }
        }
    }

    @Nullable
    static Direction getFacing(BlockState state) {
        AttributeStateFacing attr = get(state.getBlock(), AttributeStateFacing.class);
        return attr == null ? null : attr.getDirection(state);
    }

    @Nullable
    static BlockState setFacing(BlockState state, Direction facing) {
        AttributeStateFacing attr = get(state.getBlock(), AttributeStateFacing.class);
        return attr == null ? null : attr.setDirection(state, facing);
    }

    static boolean isActive(BlockState state) {
        AttributeStateActive attr = get(state.getBlock(), AttributeStateActive.class);
        return attr != null && attr.isActive(state);
    }

    @Nonnull
    static BlockState setActive(BlockState state, boolean active) {
        AttributeStateActive attr = get(state.getBlock(), AttributeStateActive.class);
        return attr == null ? state : attr.setActive(state, active);
    }

    @Nullable
    static <TIER extends ITier> TIER getTier(Block block, Class<TIER> tierClass) {
        AttributeTier<TIER> attr = get(block, AttributeTier.class);
        return attr == null ? null : attr.getTier();
    }

    @Nullable
    static BaseTier getBaseTier(Block block) {
        AttributeTier<?> attr = get(block, AttributeTier.class);
        return attr == null ? null : attr.getTier().getBaseTier();
    }
}
