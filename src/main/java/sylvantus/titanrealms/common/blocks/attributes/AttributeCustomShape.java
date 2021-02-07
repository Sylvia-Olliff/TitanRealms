package sylvantus.titanrealms.common.blocks.attributes;

import net.minecraft.util.math.shapes.VoxelShape;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute;

public class AttributeCustomShape implements IAttribute {

    private final VoxelShape[] bounds;

    public AttributeCustomShape(VoxelShape[] bounds) {
        this.bounds = bounds;
    }

    public VoxelShape[] getBounds() {
        return bounds;
    }
}
