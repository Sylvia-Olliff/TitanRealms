package sylvantus.titanrealms.core.util.interfaces.tiles;

import net.minecraft.util.Direction;
import sylvantus.titanrealms.core.util.TitanRealmsUtils;

import javax.annotation.Nonnull;

public interface ITileDirectional {

    default boolean isDirectional() { return true; }

    void setFacing(@Nonnull Direction direction);

    @Nonnull
    Direction getDirection();

    @Nonnull
    default Direction getOppositeDirection() {
        return getDirection().getOpposite();
    }

    @Nonnull
    default Direction getRightSide() {
        return TitanRealmsUtils.getRight(getDirection());
    }

    @Nonnull
    default Direction getLeftSide() {
        return TitanRealmsUtils.getLeft(getDirection());
    }
}
