package sylvantus.titanrealms.core.lib.multiblock;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mekanism.api.NBTConstants;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.pigment.IPigmentTank;
import mekanism.api.chemical.slurry.ISlurryTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.inventory.IMekanismInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sylvantus.titanrealms.common.tiles.prefabs.TileEntityInternalMultiblock;
import sylvantus.titanrealms.core.enums.EnumUtils;
import sylvantus.titanrealms.core.lib.math.voxel.IShape;
import sylvantus.titanrealms.core.lib.math.voxel.VoxelCuboid;
import sylvantus.titanrealms.core.lib.math.voxel.VoxelCuboid.CuboidRelative;
import sylvantus.titanrealms.core.lib.multiblock.FormationProtocol.StructureRequirement;
import sylvantus.titanrealms.core.network.sync.dynamic.ContainerSync;
import sylvantus.titanrealms.core.util.NBTUtils;
import sylvantus.titanrealms.core.util.WorldUtils;
import sylvantus.titanrealms.core.util.interfaces.multiblock.IStructureValidator;
import sylvantus.titanrealms.core.lib.multiblock.MultiblockCache.CacheSubstance;

public class MultiblockData implements IMekanismInventory {

    public Set<BlockPos> locations = new ObjectOpenHashSet<>();
    public final Set<BlockPos> internalLocations = new ObjectOpenHashSet<>();
//    public Set<ValveData> valves = new ObjectOpenHashSet<>();

    /**
     * @apiNote This set is only used for purposes of caching all known valid inner blocks of a multiblock structure, for use in checking if we need to revalidate the
     * multiblock when something changes, cases we want to skip are inner nodes just changing state (for example, super heating elements being activated). While we could
     * use internal locations in this case, it might not cut it for the other multiblocks that don't mark all their internal pieces as "internal multiblocks". This set is
     * not synced or checked anywhere (for things like equals) as it is only used on the server and isn't part of the structures information. It also is not the most
     * accurate of checks that get done against this as there is no way to tell if the state actually changed or if the block changed entirely, but assuming no one is
     * replacing the blocks inside of a multiblock (which is unsupported) it will handle it fine, and we can easily special case it becoming air as having been "broken"
     */
    public Set<BlockPos> innerNodes = new ObjectOpenHashSet<>();

    @ContainerSync(getter = "getVolume", setter = "setVolume")
    private int volume;

    public UUID inventoryID;

    public boolean hasMaster;

    @Nullable//may be null if structure has not been fully sent
    public BlockPos renderLocation;

    @ContainerSync
    private VoxelCuboid bounds = new VoxelCuboid(0, 0, 0);

    @ContainerSync
    private boolean formed;

    private int currentRedstoneLevel;

    private final BooleanSupplier remoteSupplier;
    private final Supplier<World> worldSupplier;

    protected final List<IInventorySlot> inventorySlots = new ArrayList<>();
//    protected final List<IExtendedFluidTank> fluidTanks = new ArrayList<>();

    public MultiblockData(TileEntity tile) {
        remoteSupplier = () -> tile.getWorld().isRemote();
        worldSupplier = tile::getWorld;
    }

    /**
     * Tick the multiblock.
     *
     * @return if we need an update packet
     */
    public boolean tick(World world) {
        boolean ret = false;
//        for (ValveData data : valves) {
//            data.activeTicks = Math.max(0, data.activeTicks - 1);
//            if (data.activeTicks > 0 != data.prevActive) {
//                ret = true;
//            }
//            data.prevActive = data.activeTicks > 0;
//        }
        return ret;
    }

    public boolean setShape(IShape shape) {
        if (shape instanceof VoxelCuboid) {
            VoxelCuboid cuboid = (VoxelCuboid) shape;
            bounds = cuboid;
            renderLocation = cuboid.getMinPos().offset(Direction.UP);
            setVolume(bounds.length() * bounds.width() * bounds.height());
            return true;
        }
        return false;
    }

    public void onCreated(World world) {
        for (BlockPos pos : internalLocations) {
            TileEntityInternalMultiblock tile = WorldUtils.getTileEntity(TileEntityInternalMultiblock.class, world, pos);
            if (tile != null) {
                tile.setMultiblock(inventoryID);
            }
        }

//        if (shouldCap(CacheSubstance.FLUID)) {
//            for (IExtendedFluidTank tank : getFluidTanks(null)) {
//                tank.setStackSize(Math.min(tank.getFluidAmount(), tank.getCapacity()), Action.EXECUTE);
//            }
//        }


        forceUpdateComparatorLevel();
    }

    protected boolean isRemote() {
        return remoteSupplier.getAsBoolean();
    }

    protected World getWorld() {
        return worldSupplier.get();
    }

    protected boolean shouldCap(CacheSubstance type) {
        return true;
    }

    public void remove(World world) {
        for (BlockPos pos : internalLocations) {
            TileEntityInternalMultiblock tile = WorldUtils.getTileEntity(TileEntityInternalMultiblock.class, world, pos);
            if (tile != null) {
                tile.setMultiblock(null);
            }
        }
        inventoryID = null;
        formed = false;
    }

    public void readUpdateTag(CompoundNBT tag) {
        NBTUtils.setIntIfPresent(tag, NBTConstants.VOLUME, this::setVolume);
        NBTUtils.setBlockPosIfPresent(tag, NBTConstants.RENDER_LOCATION, value -> renderLocation = value);
        bounds = new VoxelCuboid(NBTUtil.readBlockPos(tag.getCompound(NBTConstants.MIN)),
              NBTUtil.readBlockPos(tag.getCompound(NBTConstants.MAX)));
        NBTUtils.setUUIDIfPresentElse(tag, NBTConstants.INVENTORY_ID, value -> inventoryID = value, () -> inventoryID = null);
    }

    public void writeUpdateTag(CompoundNBT tag) {
        tag.putInt(NBTConstants.VOLUME, getVolume());
        if (renderLocation != null) {
            tag.put(NBTConstants.RENDER_LOCATION, NBTUtil.writeBlockPos(renderLocation));
        }
        tag.put(NBTConstants.MIN, NBTUtil.writeBlockPos(bounds.getMinPos()));
        tag.put(NBTConstants.MAX, NBTUtil.writeBlockPos(bounds.getMaxPos()));
        if (inventoryID != null) {
            tag.putUniqueId(NBTConstants.INVENTORY_ID, inventoryID);
        }
    }

    public int length() {
        return bounds.length();
    }

    public int width() {
        return bounds.width();
    }

    public int height() {
        return bounds.height();
    }

    public BlockPos getMinPos() {
        return bounds.getMinPos();
    }

    public BlockPos getMaxPos() {
        return bounds.getMaxPos();
    }

    public VoxelCuboid getBounds() {
        return bounds;
    }

    /**
     * Checks if this multiblock is formed and the given position is insides the bounds of this multiblock
     */
    public <T extends MultiblockData> boolean isPositionInsideBounds(@Nonnull Structure structure, @Nonnull BlockPos pos) {
        if (isFormed()) {
            CuboidRelative relativeLocation = getBounds().getRelativeLocation(pos);
            if (relativeLocation == CuboidRelative.INSIDE) {
                return true;
            } else if (relativeLocation.isWall()) {
                //If we are in the wall check if we are really an inner position. For example evap towers
                MultiblockManager<T> manager = (MultiblockManager<T>) structure.getManager();
                IStructureValidator<T> validator = manager.createValidator();
                if (validator instanceof CuboidStructureValidator) {
                    CuboidStructureValidator<T> cuboidValidator = (CuboidStructureValidator<T>) validator;
                    validator.init(getWorld(), manager, structure);
                    cuboidValidator.loadCuboid(getBounds());
                    return cuboidValidator.getStructureRequirement(pos) == StructureRequirement.INNER;
                }
            }
        }
        return false;
    }

    @Nonnull
    @Override
    public List<IInventorySlot> getInventorySlots(@Nullable Direction side) {
        return isFormed() ? inventorySlots : Collections.emptyList();
    }


    public Set<Direction> getDirectionsToEmit(BlockPos pos) {
        Set<Direction> directionsToEmit = EnumSet.noneOf(Direction.class);
        for (Direction direction : EnumUtils.DIRECTIONS) {
            if (!locations.contains(pos.offset(direction))) {
                directionsToEmit.add(direction);
            }
        }
        return directionsToEmit;
    }

//    public Collection<ValveData> getValveData() {
//        return valves;
//    }

    @Override
    public void onContentsChanged() {
    }

    @Override
    public int hashCode() {
        int code = 1;
        code = 31 * code + locations.hashCode();
        code = 31 * code + bounds.hashCode();
        code = 31 * code + getVolume();
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MultiblockData data = (MultiblockData) obj;
        if (!data.locations.equals(locations)) {
            return false;
        }
        if (!data.bounds.equals(bounds)) {
            return false;
        }
        return data.getVolume() == getVolume();
    }

    public boolean isFormed() {
        return formed;
    }

    public void setFormedForce(boolean formed) {
        this.formed = formed;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    // Only call from the server
    public void markDirtyComparator(World world) {
        if (!isFormed()) {
            return;
        }
        int newRedstoneLevel = getMultiblockRedstoneLevel();
        if (newRedstoneLevel != currentRedstoneLevel) {
            //Update the comparator value if it changed
            currentRedstoneLevel = newRedstoneLevel;
            //And inform all the valves that the level they should be supplying changed
            notifyAllUpdateComparator(world);
        }
    }

    public void notifyAllUpdateComparator(World world) {
//        for (ValveData valve : valves) {
//            TileEntityMultiblock<?> tile = WorldUtils.getTileEntity(TileEntityMultiblock.class, world, valve.location);
//            if (tile != null) {
//                tile.markDirtyComparator();
//            }
//        }
    }

    public void forceUpdateComparatorLevel() {
        currentRedstoneLevel = getMultiblockRedstoneLevel();
    }

    protected int getMultiblockRedstoneLevel() {
        return 0;
    }

    public int getCurrentRedstoneLevel() {
        return currentRedstoneLevel;
    }
}