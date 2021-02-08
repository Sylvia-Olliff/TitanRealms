package sylvantus.titanrealms.common.resources;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import sylvantus.titanrealms.core.tags.TitanRealmsTags;
import sylvantus.titanrealms.core.util.TitanRealmsUtils.ResourceType;
import sylvantus.titanrealms.core.util.interfaces.blocks.IResource;

import java.util.function.Supplier;

public enum PrimaryResource implements IResource {
    IRON("iron", 0xFFAF8E77, Tags.Items.ORES_IRON),
    GOLD("gold", 0xFFF2CD67, Tags.Items.ORES_GOLD),
    COPPER("copper", 0xFFAA4B19, () -> TitanRealmsTags.Items.ORES.get(OreType.COPPER), BlockResourceInfo.COPPER),
    ALLARUM("allarum", 0xFFC58C76, () -> TitanRealmsTags.Items.ORES.get(OreType.ALLARUM), BlockResourceInfo.ALLARUM),
    AESIRITE("aesirite", 0xFFB4A391, () -> TitanRealmsTags.Items.ORES.get(OreType.AESIRITE), BlockResourceInfo.AESIRITE),
    HELUMITE("helumite", 0xFF302A23, () -> TitanRealmsTags.Items.ORES.get(OreType.HELUMITE), BlockResourceInfo.HELUMITE);


    private final String name;
    private final int tint;
    private final Supplier<ITag<Item>> oreTag;
    private final boolean isVanilla;
    private final BlockResourceInfo resourceBlockInfo;

    PrimaryResource(String name, int tint, ITag<Item> oreTag) {
        this(name, tint, () -> oreTag, true, null);
    }

    PrimaryResource(String name, int tint, Supplier<ITag<Item>> oreTag, BlockResourceInfo resourceBlockInfo) {
        this(name, tint, oreTag, false, resourceBlockInfo);
    }

    PrimaryResource(String name, int tint, Supplier<ITag<Item>> oreTag, boolean isVanilla, BlockResourceInfo resourceBlockInfo) {
        this.name = name;
        this.tint = tint;
        this.oreTag = oreTag;
        this.isVanilla = isVanilla;
        this.resourceBlockInfo = resourceBlockInfo;
    }

    @Override
    public String getRegistrySuffix() {
        return name;
    }

    public int getTint() {
        return tint;
    }

    public ITag<Item> getOreTag() {
        return oreTag.get();
    }

    public boolean has(ResourceType type) {
        // TODO: Implement primary resource component type check
        return false;
    }

    public boolean isVanilla() {
        return isVanilla;
    }

    public BlockResourceInfo getResourceBlockInfo() {
        return resourceBlockInfo;
    }
}
