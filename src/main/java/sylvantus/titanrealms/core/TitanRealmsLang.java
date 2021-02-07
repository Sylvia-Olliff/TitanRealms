package sylvantus.titanrealms.core;

import mekanism.api.text.ILangEntry;
import net.minecraft.util.Util;
import sylvantus.titanrealms.TitanRealms;

public enum TitanRealmsLang implements ILangEntry {
    REPAIR_COST("container.repair.cost"),
    REPAIR_EXPENSIVE("container.repair.expensive"),
    // GUI Lang strings
    TITANREALMS("constants", "mod_name"),
    LOG_FORMAT("constants", "log_format"),
    FORGE("constants", "forge"),
    ALPHA_WARNING("constants", "alpha_warning"),
    ALPHA_WARNING_HERE("constants", "alpha_warning.here"),
    //Equipment
    HEAD("equipment", "head"),
    BODY("equipment", "body"),
    LEGS("equipment", "legs"),
    FEET("equipment", "feet"),
    MAINHAND("equipment", "mainhand"),
    OFFHAND("equipment", "offhand"),
    // Compass Directions
    NORTH_SHORT("direction", "north.short"),
    SOUTH_SHORT("direction", "south.short"),
    WEST_SHORT("direction", "west.short"),
    EAST_SHORT("direction", "east.short"),
    // JEI
    JEI_AMOUNT_WITH_CAPACITY("tooltip", "jei.amount.with.capacity"),
    // Key
    KEY_DETAILS_MODE("key", "details"),
    KEY_DESCRIPTION_MODE("key", "description"),
    // Generic
    GENERIC_WITH_COMMA("generic", "with_comma"),
    GENERIC_STORED("generic", "stored"),
    GENERIC_STORED_MB("generic", "stored.mb"),
    GENERIC_MB("generic", "mb"),
    GENERIC_PRE_COLON("generic", "pre_colon"),
    GENERIC_SQUARE_BRACKET("generic", "square_bracket"),
    GENERIC_PARENTHESIS("generic", "parenthesis"),
    GENERIC_WITH_PARENTHESIS("generic", "with_parenthesis"),
    GENERIC_WITH_TWO_PARENTHESIS("generic", "with_two_parenthesis"),
    GENERIC_FRACTION("generic", "fraction"),
    GENERIC_TRANSFER("generic", "transfer"),
    GENERIC_PER_TICK("generic", "per_tick"),
    GENERIC_PRE_STORED("generic", "pre_pre_colon"),
    GENERIC_BLOCK_POS("generic", "block_pos"),
    GENERIC_HEX("generic", "hex"),
    // Hold for
    HOLD_FOR_DETAILS("tooltip", "hold_for_details"),
    HOLD_FOR_DESCRIPTION("tooltip", "hold_for_description"),
    HOLD_FOR_SUPPORTED_ITEMS("tooltip", "hold_for_supported_items"),
    // Commands

    // Side data/config
    SIDE_DATA_NONE("side_data", "none"),
    // Tooltip stuff
    ITEM_AMOUNT("tooltip", "item_amount"),
    INVALID("tooltip", "invalid"),
    HAS_INVENTORY("tooltip", "inventory"),
    PROGRESS("gui", "progress"),
    PROCESS_RATE("gui", "process_rate"),
    PROCESS_RATE_MB("gui", "process_rate_mb"),
    // GUI stuff
    HEIGHT("gui", "height"),
    WIDTH("gui", "width"),
    TICKS_REQUIRED("gui", "ticks_required"),
    MIN("gui", "min"),
    MAX("gui", "max"),
    INFINITE("gui", "infinite"),
    NONE("gui", "none"),
    EMPTY("gui", "empty"),
    STORING("gui", "storing"),
    UNIT("gui", "unit"),
    USING("gui", "using"),
    NEEDED("gui", "needed"),
    NEEDED_PER_TICK("gui", "needed_per_tick"),
    FINISHED("gui", "finished"),
    NO_RECIPE("gui", "no_recipe"),
    MOVE_UP("gui", "move_up"),
    MOVE_DOWN("gui", "move_down"),
    SET("gui", "set"),
    TRUE("gui", "true"),
    FALSE("gui", "false"),
    CLOSE("gui", "close"),
    COLOR_PICKER("gui", "color_picker"),
    RGB("gui", "rgb"),
    OPACITY("gui", "opacity"),
    DEFAULT("gui", "default"),
    WARNING("gui", "warning"),
    DANGER("gui", "danger"),
    COMPASS("gui", "compass"),
    // Tab
    MAIN_TAB("tab", "main"),
    // Status
    STATUS("status", "format"),
    STATUS_OK("status", "ok"),
    // Boolean values
    YES("boolean", "yes"),
    NO("boolean", "no"),
    ON("boolean", "on"),
    OFF("boolean", "off"),
    INPUT("boolean", "input"),
    OUTPUT("boolean", "output"),
    ACTIVE("boolean", "active"),
    DISABLED("boolean", "disabled"),
    ON_CAPS("boolean", "on_caps"),
    OFF_CAPS("boolean", "off_caps"),
    // Capacity
    CAPACITY("capacity", "generic"),
    CAPACITY_ITEMS("capacity", "items"),
    CAPACITY_MB("capacity", "mb"),
    CAPACITY_PER_TICK("capacity", "per_tick"),
    CAPACITY_MB_PER_TICK("capacity", "mb.per_tick"),
    // Crafting Formula
    INGREDIENTS("crafting_formula", "ingredients"),
    ENCODED("crafting_formula", "encoded"),
    //Multiblock
    MULTIBLOCK_INCOMPLETE("multiblock", "incomplete"),
    MULTIBLOCK_FORMED("multiblock", "formed"),
    MULTIBLOCK_CONFLICT("multiblock", "conflict"),
    MULTIBLOCK_FORMED_CHAT("multiblock", "formed.chat"),
    MULTIBLOCK_INVALID_FRAME("multiblock", "invalid_frame"),
    MULTIBLOCK_INVALID_INNER("multiblock", "invalid_inner"),
    // Button
    BUTTON_CONFIRM("button", "confirm"),
    BUTTON_START("button", "start"),
    BUTTON_STOP("button", "stop"),
    BUTTON_CONFIG("button", "config"),
    BUTTON_REMOVE("button", "remove"),
    BUTTON_CANCEL("button", "cancel"),
    BUTTON_SAVE("button", "save"),
    BUTTON_SET("button", "set"),
    BUTTON_DELETE("button", "delete"),
    BUTTON_OPTIONS("button", "options"),
    // Descriptions
    DESCRIPTION_TITANFORGE("description", "titan_forge"),
    DESCRIPTION_TITANBRONZE("description", "titan_bronze"),
    DESCRIPTION_TITANSTEEL("description", "titan_steel");

    private final String key;

    TitanRealmsLang(String type, String path) {
        this(Util.makeTranslationKey(type, TitanRealms.rl(path)));
    }

    TitanRealmsLang(String key) {
        this.key = key;
    }

    @Override
    public String getTranslationKey() { return key; }
}
