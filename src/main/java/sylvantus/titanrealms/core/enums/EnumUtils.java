package sylvantus.titanrealms.core.enums;

import mekanism.api.RelativeSide;
import net.minecraft.util.Direction;

public class EnumUtils {

    private EnumUtils() {}

    public static final Direction[] DIRECTIONS = Direction.values();

    public static final Direction[] HORIZONTAL_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    public static final RelativeSide[] SIDES = RelativeSide.values();


}
