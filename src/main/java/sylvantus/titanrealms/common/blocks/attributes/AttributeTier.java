package sylvantus.titanrealms.common.blocks.attributes;

import sylvantus.titanrealms.common.blocks.blocktypes.BlockType.BlockTypeBuilder;
import sylvantus.titanrealms.common.blocks.blocktypes.BlockType;
import sylvantus.titanrealms.core.TitanRealmsLang;
import sylvantus.titanrealms.core.util.interfaces.ITier;
import sylvantus.titanrealms.core.util.interfaces.attributes.IAttribute;

import java.util.HashMap;
import java.util.Map;

public class AttributeTier<TIER extends ITier> implements IAttribute {

    private final TIER tier;

    public AttributeTier(TIER tier) {
        this.tier = tier;
    }

    public TIER getTier() {
        return tier;
    }

    // TODO remove this, eventually we'll natively use BlockType in transmitters
    private static final Map<ITier, BlockType> typeCache = new HashMap<>();

    public static <T extends ITier> BlockType getPassthroughType(T tier) {
        if (typeCache.containsKey(tier)) {
            return typeCache.get(tier);
        }
        BlockType type = BlockTypeBuilder.createBlock(TitanRealmsLang.EMPTY).with(new AttributeTier<>(tier)).build();
        typeCache.put(tier, type);
        return type;
    }
}

