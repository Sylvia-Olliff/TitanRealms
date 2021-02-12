package sylvantus.titanrealms.core.enums;

import mekanism.api.RelativeSide;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Direction;
import sylvantus.titanrealms.common.resources.*;

public class EnumUtils {

    private EnumUtils() {}

    public static final Direction[] DIRECTIONS = Direction.values();

    public static final Direction[] HORIZONTAL_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    public static final RelativeSide[] SIDES = RelativeSide.values();

    public static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET};

    public static final OreType[] ORE_TYPES = OreType.values();

    public static final ResourceType[] RESOURCE_TYPES = ResourceType.values();

    public static final PrimaryResource[] PRIMARY_RESOURCES = PrimaryResource.values();

    public static final TerrainResource[] TERRAIN_RESOURCES = TerrainResource.values();

    public static final TerrainType[] TERRAIN_TYPES = TerrainType.values();

    public static final EquipmentSlotType[] EQUIPMENT_SLOT_TYPES = EquipmentSlotType.values();
}
