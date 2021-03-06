package sylvantus.titanrealms.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.particle.DiggingParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sylvantus.titanrealms.common.blocks.attributes.AttributeStateFacing;
import sylvantus.titanrealms.common.blocks.states.BlockStateHelper;
import sylvantus.titanrealms.common.tiles.base.TileEntityTitanRealms;
import sylvantus.titanrealms.core.util.WorldUtils;
import sylvantus.titanrealms.core.util.interfaces.ISustainedInventory;
import sylvantus.titanrealms.core.util.interfaces.blocks.IHasTileEntity;
import sylvantus.titanrealms.core.util.interfaces.tiles.ISustainedData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class TitanRealmsBlock extends Block {

    protected TitanRealmsBlock(AbstractBlock.Properties properties) {
        super(BlockStateHelper.applyLightLevelAdjustments(properties));
        setDefaultState(BlockStateHelper.getDefaultState(stateContainer.getBaseState()));
    }

    @Nonnull
    @Override
    @Deprecated
    public PushReaction getPushReaction(@Nonnull BlockState state) {
        if (hasTileEntity(state)) {
            return PushReaction.BLOCK;
        }
        return super.getPushReaction(state);
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull BlockState state, RayTraceResult target, @Nonnull IBlockReader world, @Nonnull BlockPos pos, PlayerEntity player) {
        ItemStack itemStack = new ItemStack(this);
        TileEntityTitanRealms tile = WorldUtils.getTileEntity(TileEntityTitanRealms.class, world, pos);
        if (tile == null) {
            return itemStack;
        }
        Item item = itemStack.getItem();
//        if (tile.supportsUpgrades()) {
//            tile.getComponent().write(ItemDataUtils.getDataMap(itemStack));
//        }
//        if (tile instanceof ISideConfiguration) {
//            ISideConfiguration config = (ISideConfiguration) tile;
//            config.getConfig().write(ItemDataUtils.getDataMap(itemStack));
//            config.getEjector().write(ItemDataUtils.getDataMap(itemStack));
//        }
        if (tile instanceof ISustainedData) {
            ((ISustainedData) tile).writeSustainedData(itemStack);
        }
//        if (tile.supportsRedstone()) {
//            ItemDataUtils.setInt(itemStack, NBTConstants.CONTROL_TYPE, tile.getControlType().ordinal());
//        }
//        for (SubstanceType type : EnumUtils.SUBSTANCES) {
//            if (tile.handles(type)) {
//                ItemDataUtils.setList(itemStack, type.getContainerTag(), DataHandlerUtils.writeContainers(type.getContainers(tile)));
//            }
//        }
        if (item instanceof ISustainedInventory && tile.persistInventory() && tile.getSlots() > 0) {
            ((ISustainedInventory) item).setInventory(tile.getInventory(), itemStack);
        }
        return itemStack;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return this instanceof IHasTileEntity;
    }

    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        if (this instanceof IHasTileEntity) {
            return ((IHasTileEntity<?>) this).getTileType().create();
        }
        return null;
    }

    @Override
    protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        BlockStateHelper.fillBlockStateContainer(this, builder);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return BlockStateHelper.getStateForPlacement(this, super.getStateForPlacement(context), context);
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        TileEntityTitanRealms tile = WorldUtils.getTileEntity(TileEntityTitanRealms.class, world, pos);
        if (tile == null) {
            return;
        }
//        if (tile.supportsRedstone()) {
//            tile.redstone = world.isBlockPowered(pos);
//        }

        tile.onPlace();

        //Handle item
        Item item = stack.getItem();
        setTileData(world, pos, state, placer, stack, tile);

//        if (tile.supportsUpgrades()) {
//            //The read method validates that data is stored
//            tile.getComponent().read(ItemDataUtils.getDataMap(stack));
//        }

//        for (SubstanceType type : EnumUtils.SUBSTANCES) {
//            if (type.canHandle(tile)) {
//                DataHandlerUtils.readContainers(type.getContainers(tile), ItemDataUtils.getList(stack, type.getContainerTag()));
//            }
//        }
        if (tile instanceof ISustainedData && stack.hasTag()) {
            ((ISustainedData) tile).readSustainedData(stack);
        }
//        if (tile.supportsRedstone() && ItemDataUtils.hasData(stack, NBTConstants.CONTROL_TYPE, NBT.TAG_INT)) {
//            tile.setControlType(RedstoneControl.byIndexStatic(ItemDataUtils.getInt(stack, NBTConstants.CONTROL_TYPE)));
//        }
        if (item instanceof ISustainedInventory && tile.persistInventory()) {
            tile.setInventory(((ISustainedInventory) item).getInventory(stack));
        }
    }

    //Method to override for setting some simple tile specific stuff
    public void setTileData(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack, TileEntityTitanRealms tile) {
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation rotation) {
        return AttributeStateFacing.rotate(state, world, pos, rotation);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
        //Copy of ParticleManager#addBlockDestroyEffects, but removes the minimum number of particles each voxel shape produces
        state.getShape(world, pos).forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
            double xDif = Math.min(1, maxX - minX);
            double yDif = Math.min(1, maxY - minY);
            double zDif = Math.min(1, maxZ - minZ);
            //Don't force the counts to be at least two
            int xCount = MathHelper.ceil(xDif / 0.25);
            int yCount = MathHelper.ceil(yDif / 0.25);
            int zCount = MathHelper.ceil(zDif / 0.25);
            if (xCount > 0 && yCount > 0 && zCount > 0) {
                for (int x = 0; x < xCount; x++) {
                    for (int y = 0; y < yCount; y++) {
                        for (int z = 0; z < zCount; z++) {
                            double d4 = (x + 0.5) / xCount;
                            double d5 = (y + 0.5) / yCount;
                            double d6 = (z + 0.5) / zCount;
                            double d7 = d4 * xDif + minX;
                            double d8 = d5 * yDif + minY;
                            double d9 = d6 * zDif + minZ;
                            manager.addEffect(new DiggingParticle((ClientWorld) world, pos.getX() + d7, pos.getY() + d8,
                                    pos.getZ() + d9, d4 - 0.5, d5 - 0.5, d6 - 0.5, state).setBlockPos(pos));
                        }
                    }
                }
            }
        });
        return true;
    }
}
