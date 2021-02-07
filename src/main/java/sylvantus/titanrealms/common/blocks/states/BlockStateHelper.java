package sylvantus.titanrealms.common.blocks.states;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.jetbrains.annotations.Contract;

import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttributeState;
import sylvantus.titanrealms.core.util.interfaces.blocks.IStateStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

public class BlockStateHelper {

    private BlockStateHelper() {
    }

    // Originally used by Mekanism for their cardboard box storage. I might still find use for it.
    public static final BooleanProperty storageProperty = BooleanProperty.create("storage");

    public static BlockState getDefaultState(@Nonnull BlockState state) {
        Block block = state.getBlock();
        for (IAttribute attr : IAttribute.getAll(block)) {
            if (attr instanceof IAttributeState) {
                state = ((IAttributeState) attr).getDefaultState(state);
            }
        }
//        if (block instanceof IStateFluidLoggable) {
//            //Default the blocks to not being waterlogged, they have code to force waterlogging to true if being placed in water
//            state = state.with(((IStateFluidLoggable) block).getFluidLoggedProperty(), 0);
//        }
        return state;
    }

    public static void fillBlockStateContainer(Block block, StateContainer.Builder<Block, BlockState> builder) {
        List<Property<?>> properties = new ArrayList<>();
        for (IAttribute attr : IAttribute.getAll(block)) {
            if (attr instanceof IAttributeState) {
                ((IAttributeState) attr).fillBlockStateContainer(block, properties);
            }
        }
        if (block instanceof IStateStorage) {
            properties.add(storageProperty);
        }
//        if (block instanceof IStateFluidLoggable) {
//            properties.add(((IStateFluidLoggable) block).getFluidLoggedProperty());
//        }
        if (!properties.isEmpty()) {
            builder.add(properties.toArray(new Property[0]));
        }
    }

    /**
     * Helper to "hack" in and modify the light value precalculator for states to be able to use as a base level the value already set, but also modify it based on which
     * fluid a block may be fluid logged with and then use that light level instead if it is higher.
     */
    public static AbstractBlock.Properties applyLightLevelAdjustments(AbstractBlock.Properties properties) {
        //Cache what the current light level function is
        ToIntFunction<BlockState> existingLightLevelFunction = properties.lightLevel;
        //And override the one in the properties in a way that we can modify if we have state information that should adjust it
        return properties.setLightLevel(state -> {
            int light = existingLightLevelFunction.applyAsInt(state);
            Block block = state.getBlock();
//            if (block instanceof IStateFluidLoggable) {
//                light = Math.max(light, ((IStateFluidLoggable) block).getFluid(state).getFluid().getAttributes().getLuminosity());
//            }
            return light;
        });
    }

    @Contract("_, null, _ -> null")
    public static BlockState getStateForPlacement(Block block, @Nullable BlockState state, BlockItemUseContext context) {
        return getStateForPlacement(block, state, context.getWorld(), context.getPos(), context.getPlayer(), context.getFace());
    }

    @Contract("_, null, _, _, _, _ -> null")
    public static BlockState getStateForPlacement(Block block, @Nullable BlockState state, @Nonnull IWorld world, @Nonnull BlockPos pos, @Nullable PlayerEntity player, @Nonnull Direction face) {
        if (state == null) {
            return null;
        }
        for (IAttribute attr : IAttribute.getAll(block)) {
            if (attr instanceof IAttributeState) {
                state = ((IAttributeState) attr).getStateForPlacement(block, state, world, pos, player, face);
            }
        }
//        if (block instanceof IStateFluidLoggable) {
//            IStateFluidLoggable fluidLoggable = (IStateFluidLoggable) block;
//            FluidState fluidState = world.getFluidState(pos);
//            state = state.with(fluidLoggable.getFluidLoggedProperty(), fluidLoggable.getSupportedFluidPropertyIndex(fluidState.getFluid()));
//        }
        return state;
    }

    public static BlockState copyStateData(BlockState oldState, BlockState newState) {
        Block oldBlock = oldState.getBlock();
        Block newBlock = newState.getBlock();
        for (IAttribute attr : IAttribute.getAll(oldBlock)) {
            if (attr instanceof IAttributeState) {
                newState = ((IAttributeState) attr).copyStateData(oldState, newState);
            }
        }
        if (oldBlock instanceof IStateStorage && newBlock instanceof IStateStorage) {
            newState = newState.with(storageProperty, oldState.get(storageProperty));
        }
//        if (oldBlock instanceof IStateFluidLoggable && newBlock instanceof IStateFluidLoggable) {
//            IStateFluidLoggable oldFluidLoggable = (IStateFluidLoggable) oldBlock;
//            IStateFluidLoggable newFluidLoggable = (IStateFluidLoggable) newBlock;
//            if (oldFluidLoggable.getSupportedFluids().length == newFluidLoggable.getSupportedFluids().length) {
//                //Basic check if the number of supported fluids is the same copy it over
//                //TODO: Eventually maybe we want a better check? In theory they should always match but just in case
//                newState = newState.with(newFluidLoggable.getFluidLoggedProperty(), oldState.get(oldFluidLoggable.getFluidLoggedProperty()));
//            }
//        }
        return newState;
    }
}
