package sylvantus.titanrealms.common.tiles.base;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import mekanism.api.DataHandlerUtils;
import mekanism.api.NBTConstants;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.inventory.IMekanismInventory;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.text.TextComponentUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.network.NetworkHooks;
import sylvantus.titanrealms.client.sound.SoundHandler;
import sylvantus.titanrealms.common.blocks.attributes.AttributeGui;
import sylvantus.titanrealms.common.blocks.attributes.AttributeSound;
import sylvantus.titanrealms.common.blocks.attributes.AttributeStateActive;
import sylvantus.titanrealms.common.blocks.attributes.AttributeStateFacing;
import sylvantus.titanrealms.common.capabilities.resolver.manager.ItemHandlerManager;
import sylvantus.titanrealms.common.containers.tiles.TitanRealmsContainer;
import sylvantus.titanrealms.core.util.interfaces.ISustainedInventory;
import sylvantus.titanrealms.core.util.interfaces.capabilities.IInventorySlotHolder;
import sylvantus.titanrealms.core.util.interfaces.capabilities.manager.ICapabilityHandlerManager;
import sylvantus.titanrealms.core.config.TitanRealmsConfig;
import sylvantus.titanrealms.core.config.tile.components.TileComponentConfig;
import sylvantus.titanrealms.core.util.NBTUtils;
import sylvantus.titanrealms.core.util.WorldUtils;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute;
import sylvantus.titanrealms.core.util.interfaces.blocks.IHasTileEntity;
import sylvantus.titanrealms.core.util.interfaces.containers.ITrackableContainer;
import sylvantus.titanrealms.core.util.interfaces.tiles.ITileActive;
import sylvantus.titanrealms.core.util.interfaces.tiles.ITileComponent;
import sylvantus.titanrealms.core.util.interfaces.tiles.ITileDirectional;
import sylvantus.titanrealms.core.util.interfaces.tiles.ITileSound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.IntSupplier;

public abstract class TileEntityTitanRealms extends CapabilityTileEntity implements ITickableTileEntity, ITileActive, ITileDirectional,
        ITileSound, IMekanismInventory, ISustainedInventory, ITrackableContainer {

    public final Set<PlayerEntity> playersUsing = new ObjectOpenHashSet<>();

    public int ticker;
    private final List<ICapabilityHandlerManager<?>> capabilityHandlerManagers = new ArrayList<>();
    private final List<ITileComponent> components = new ArrayList<>();

    protected final IBlockProvider blockProvider;

    //Variables for handling ITileContainer
    protected final ItemHandlerManager itemHandlerManager;
    //End variables ITileContainer

//    private boolean supportsRedstone;
    private boolean isDirectional;
    private boolean isActivatable;
    private boolean hasSound;
    private boolean hasGui;

    //Variables for handling ITileActive
    private boolean currentActive;
    private int updateDelay;
    protected IntSupplier delaySupplier = TitanRealmsConfig.general.blockDeactivationDelay;
    //End variables ITileActive

    //Variables for handling ITileSound
    @Nullable
    private final SoundEvent soundEvent;

    /**
     * Only used on the client
     */
    private ISound activeSound;
    private int playSoundCooldown = 0;
    //End variables ITileSound

    @SuppressWarnings("unchecked cast")
    public TileEntityTitanRealms(IBlockProvider blockProvider) {
        super(((IHasTileEntity<? extends TileEntity>) blockProvider.getBlock()).getTileType());
        this.blockProvider = blockProvider;
        setSupportedTypes(this.blockProvider.getBlock());
        presetVariables();
        capabilityHandlerManagers.add(itemHandlerManager = new ItemHandlerManager(getInitialInventory(), this));

        if (hasSound()) {
            soundEvent = IAttribute.get(blockProvider.getBlock(), AttributeSound.class).getSoundEvent();
        } else {
            soundEvent = null;
        }
    }

    private void setSupportedTypes(Block block) {
        isDirectional = IAttribute.has(block, AttributeStateFacing.class);
        hasSound = IAttribute.has(block, AttributeSound.class);
        hasGui = IAttribute.has(block, AttributeGui.class);
        isActivatable = hasSound || IAttribute.has(block, AttributeStateActive.class);
    }

    protected void presetVariables() {
    }

    public Block getBlockType() {
        return blockProvider.getBlock();
    }

    @Override
    public final boolean isDirectional() {
        return isDirectional;
    }

//    @Override
//    public final boolean supportsRedstone() {
//        return supportsRedstone;
//    }

    @Override
    public final boolean hasSound() {
        return hasSound;
    }

    public final boolean hasGui() {
        return hasGui;
    }

    @Override
    public final boolean isActivatable() {
        return isActivatable;
    }

    @Override
    public final boolean hasInventory() {
        return itemHandlerManager.canHandle();
    }

    public void addComponent(ITileComponent component) {
        components.add(component);
        if (component instanceof TileComponentConfig) {
            addConfigComponent((TileComponentConfig) component);
        }
    }

    public List<ITileComponent> getComponents() {
        return components;
    }

    @Nonnull
    public ITextComponent getName() {
        return TextComponentUtil.translate(Util.makeTranslationKey("container", getBlockType().getRegistryName()));
    }

    public ActionResultType openGui(PlayerEntity player) {
        //Everything that calls this has isRemote being false but add the check just in case anyways
        if (hasGui() && !isRemote() && !player.isSneaking()) {
            //Pass on this activation if the player is rotating with a configurator
            ItemStack stack = player.getHeldItemMainhand();

            NetworkHooks.openGui((ServerPlayerEntity) player, IAttribute.get(blockProvider.getBlock(), AttributeGui.class).getProvider(this), pos);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void tick() {
        for (ITileComponent component : components) {
            component.tick();
        }
        if (isRemote()) {
            if (hasSound()) {
                updateSound();
            }
            if (isActivatable()) {
                if (ticker == 0) {
                    WorldUtils.updateBlock(getWorld(), getPos());
                }
            }
            onUpdateClient();
        } else {
            if (isActivatable()) {
                if (updateDelay > 0) {
                    updateDelay--;
                    if (updateDelay == 0 && getClientActive() != currentActive) {
                        //If it doesn't match and we are done with the delay period, then update it
                        world.setBlockState(pos, IAttribute.setActive(getBlockState(), currentActive));
                    }
                }
            }
            onUpdateServer();
        }
        ticker++;
//        if (supportsRedstone()) {
//            redstoneLastTick = redstone;
//        }
    }

    public void open(PlayerEntity player) {
        playersUsing.add(player);
    }

    public void close(PlayerEntity player) {
        playersUsing.remove(player);
    }

    @Override
    public void remove() {
        super.remove();
        for (ITileComponent component : components) {
            component.invalidate();
        }
        if (isRemote() && hasSound()) {
            updateSound();
        }
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        for (ITileComponent component : components) {
            component.onChunkLoaded();
        }
    }

    /**
     * Update call for machines. Use instead of updateEntity -- it's called every tick on the client side.
     */
    protected void onUpdateClient() {
    }

    /**
     * Update call for machines. Use instead of updateEntity -- it's called every tick on the server side.
     */
    protected void onUpdateServer() {
    }

    public void onPlace() {
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT nbtTags) {
        super.read(state, nbtTags);
//        NBTUtils.setBooleanIfPresent(nbtTags, NBTConstants.REDSTONE, value -> redstone = value);
        for (ITileComponent component : components) {
            component.read(nbtTags);
        }
//        if (supportsRedstone()) {
//            NBTUtils.setEnumIfPresent(nbtTags, NBTConstants.CONTROL_TYPE, RedstoneControl::byIndexStatic, type -> controlType = type);
//        }
        if (hasInventory() && persistInventory()) {
            DataHandlerUtils.readContainers(getInventorySlots(null), nbtTags.getList(NBTConstants.ITEMS, NBT.TAG_COMPOUND));
        }

        if (isActivatable()) {
            NBTUtils.setBooleanIfPresent(nbtTags, NBTConstants.ACTIVE_STATE, value -> currentActive = value);
            NBTUtils.setIntIfPresent(nbtTags, NBTConstants.UPDATE_DELAY, value -> updateDelay = value);
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT nbtTags) {
        super.write(nbtTags);
//        nbtTags.putBoolean(NBTConstants.REDSTONE, redstone);
        for (ITileComponent component : components) {
            component.write(nbtTags);
        }
//        if (supportsRedstone()) {
//            nbtTags.putInt(NBTConstants.CONTROL_TYPE, controlType.ordinal());
//        }
//        if (hasInventory() && persistInventory()) {
//            nbtTags.put(NBTConstants.ITEMS, DataHandlerUtils.writeContainers(getInventorySlots(null)));
//        }

        if (isActivatable()) {
            nbtTags.putBoolean(NBTConstants.ACTIVE_STATE, currentActive);
            nbtTags.putInt(NBTConstants.UPDATE_DELAY, updateDelay);
        }
//        if (supportsComparator()) {
//            nbtTags.putInt(NBTConstants.CURRENT_REDSTONE, currentRedstoneLevel);
//        }
        return nbtTags;
    }

    @Override
    public void addContainerTrackers(TitanRealmsContainer container) {

    }

    @Nonnull
    @Override
    public CompoundNBT getReducedUpdateTag() {
        CompoundNBT updateTag = super.getReducedUpdateTag();
        for (ITileComponent component : components) {
            component.addToUpdateTage(updateTag);
        }
        return updateTag;
    }

    @Override
    public void handleUpdateTag(BlockState state, @Nonnull CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        for (ITileComponent component : components) {
            component.readFromUpdateTag(tag);
        }
    }

    public void onNeighborChange(Block block, BlockPos neighborPos) {
//        if (!isRemote() && supportsRedstone()) {
//            updatePower();
//        }
    }

    /**
     * Called when block is placed in world
     */
    public void onAdded() {
//        if (supportsRedstone()) {
//            updatePower();
//        }
    }

    //Methods for implementing ITileDirectional
    @Nonnull
    @Override
    public Direction getDirection() {
        if (isDirectional()) {
            Direction facing = IAttribute.getFacing(getBlockState());
            return facing != null ? facing : Direction.NORTH;
        }
        //TODO: Remove, give it some better default, or allow it to be null
        return Direction.NORTH;
    }

    @Override
    public void setFacing(@Nonnull Direction direction) {
        if (isDirectional()) {
            BlockState state = IAttribute.setFacing(getBlockState(), direction);
            if (world != null && state != null) {
                world.setBlockState(pos, state);
            }
        }
    }
    //End methods ITileDirectional

    //Methods for implementing ITileContainer
    @Nullable
    protected IInventorySlotHolder getInitialInventory() {
        return null;
    }

    @Nonnull
    @Override
    public final List<IInventorySlot> getInventorySlots(@Nullable Direction side) {
        return itemHandlerManager.getContainers(side);
    }

    @Override
    public void onContentsChanged() {
        markDirty(false);
    }

    @Override
    public void setInventory(ListNBT nbtTags, Object... data) {
        if (nbtTags != null && !nbtTags.isEmpty() && persistInventory()) {
            DataHandlerUtils.readContainers(getInventorySlots(null), nbtTags);
        }
    }

    @Override
    public ListNBT getInventory(Object... data) {
        return persistInventory() ? DataHandlerUtils.writeContainers(getInventorySlots(null)) : new ListNBT();
    }

    /**
     * Should the inventory be persisted in this tile save
     */
    public boolean persistInventory() {
        return hasInventory();
    }
    //End methods ITileContainer

    //Methods for implementing ITileActive
    @Override
    public boolean getActive() {
        return isRemote() ? getClientActive() : currentActive;
    }

    private boolean getClientActive() {
        return IAttribute.isActive(getBlockState());
    }

    @Override
    public void setActive(boolean active) {
        if (isActivatable()) {
            BlockState state = getBlockState();
            Block block = state.getBlock();
            if (active != currentActive && IAttribute.has(block, AttributeStateActive.class)) {
                currentActive = active;
                if (getClientActive() != active) {
                    if (active) {
                        //Always turn on instantly
                        state = IAttribute.setActive(state, true);
                        world.setBlockState(pos, state);
                    } else {
                        // if the update delay is already zero, we can go ahead and set the state
                        if (updateDelay == 0) {
                            world.setBlockState(pos, IAttribute.setActive(getBlockState(), currentActive));
                        }
                        // we always reset the update delay when turning off
                        updateDelay = delaySupplier.getAsInt();
                    }
                }
            }
        }
    }
    //End methods ITileActive

    //Methods for implementing ITileSound

    /**
     * Used to check if this tile should attempt to play its sound
     */
    protected boolean canPlaySound() {
        return getActive();
    }

    /**
     * Only call this from the client
     */
    private void updateSound() {
        // If machine sounds are disabled, noop
        if (!hasSound() || !TitanRealmsConfig.client.enableMachineSounds.get() || soundEvent == null) {
            return;
        }
        if (canPlaySound() && !isRemoved()) {
            // If sounds are being muted, we can attempt to start them on every tick, only to have them
            // denied by the event bus, so use a cooldown period that ensures we're only trying once every
            // second or so to start a sound.
            if (--playSoundCooldown > 0) {
                return;
            }

            // If this machine isn't fully muffled and we don't seem to be playing a sound for it, go ahead and
            // play it
            if (!isFullyMuffled() && (activeSound == null || !Minecraft.getInstance().getSoundHandler().isPlaying(activeSound))) {
                activeSound = SoundHandler.startTileSound(soundEvent, getSoundCategory(), getInitialVolume(), getSoundPos());
            }
            // Always reset the cooldown; either we just attempted to play a sound or we're fully muffled; either way
            // we don't want to try again
            playSoundCooldown = 20;
        } else if (activeSound != null) {
            SoundHandler.stopTileSound(getSoundPos());
            activeSound = null;
            playSoundCooldown = 0;
        }
    }

    private boolean isFullyMuffled() {
        if (!hasSound()) {
            return false;
        }
        // TODO: This needs more awareness of outside attempts to muffle sound
        return false;
    }
    //End methods ITileSound
}
