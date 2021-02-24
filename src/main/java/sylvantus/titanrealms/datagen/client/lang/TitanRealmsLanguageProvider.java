package sylvantus.titanrealms.datagen.client.lang;

import mekanism.api.text.EnumColor;
import net.minecraft.data.DataGenerator;
import sylvantus.titanrealms.TitanRealms;
import sylvantus.titanrealms.common.resources.TerrainType;
import sylvantus.titanrealms.core.TitanRealmsLang;
import sylvantus.titanrealms.core.registries.TitanRealmsBlocks;

public class TitanRealmsLanguageProvider extends BaseLanguageProvider {

    public TitanRealmsLanguageProvider(DataGenerator gen) {
        super(gen, TitanRealms.MODID);
    }

    @Override
    protected void addTranslations() {
        addItems();
        addBlocks();
        addEntities();
        addDamageSources();
        addSubtitles();
        addMisc();
    }

    private static String formatAndCapitalize(String s) {
        boolean isFirst = true;
        StringBuilder ret = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '_') {
                isFirst = true;
                ret.append(' ');
            } else {
                ret.append(isFirst ? Character.toUpperCase(c) : c);
                isFirst = false;
            }
        }
        return ret.toString();
    }

    private void addMisc() {
        //Colors
        for (EnumColor color : EnumColor.values()) {
            add(color.getLangEntry(), color.getEnglishName());
        }

        add(TitanRealmsLang.TITANREALMS, TitanRealms.MOD_NAME);
        add(TitanRealmsLang.LOG_FORMAT, "[%s] %s");
        add(TitanRealmsLang.ALPHA_WARNING, "Warning: Titan Realm's is currently in alpha, and is not recommended for widespread use in modpacks. There are likely to be game breaking bugs, and various other issues that you can read more about %s.");
        add(TitanRealmsLang.ALPHA_WARNING_HERE, "here");
        // Equipment
        add(TitanRealmsLang.HEAD, "Head");
        add(TitanRealmsLang.BODY, "Body");
        add(TitanRealmsLang.LEGS, "Legs");
        add(TitanRealmsLang.FEET, "Feet");
        add(TitanRealmsLang.MAINHAND, "Hand 1");
        add(TitanRealmsLang.OFFHAND, "Hand 2");
        // JEI
        add(TitanRealmsLang.JEI_AMOUNT_WITH_CAPACITY, "%s / %s mB");
        // Key
        add(TitanRealmsLang.KEY_DETAILS_MODE, "Show Details");
        add(TitanRealmsLang.KEY_DESCRIPTION_MODE, "Show Description");
        // Generic
        add(TitanRealmsLang.GENERIC_WITH_COMMA, "%s, %s");
        add(TitanRealmsLang.GENERIC_STORED, "%s: %s");
        add(TitanRealmsLang.GENERIC_STORED_MB, "%s: %s mB");
        add(TitanRealmsLang.GENERIC_MB, "%s mB");
        add(TitanRealmsLang.GENERIC_PRE_COLON, "%s:");
        add(TitanRealmsLang.GENERIC_SQUARE_BRACKET, "[%s]");
        add(TitanRealmsLang.GENERIC_PARENTHESIS, "(%s)");
        add(TitanRealmsLang.GENERIC_WITH_PARENTHESIS, "%s (%s)");
        add(TitanRealmsLang.GENERIC_WITH_TWO_PARENTHESIS, "%s (%s) (%s)");
        add(TitanRealmsLang.GENERIC_FRACTION, "%s/%s");
        add(TitanRealmsLang.GENERIC_TRANSFER, "- %s (%s)");
        add(TitanRealmsLang.GENERIC_PER_TICK, "%s/t");
        add(TitanRealmsLang.GENERIC_PRE_STORED, "%s %s: %s");
        add(TitanRealmsLang.GENERIC_BLOCK_POS, "%s, %s, %s");
        add(TitanRealmsLang.GENERIC_HEX, "#%s");
        // Directions
        add(TitanRealmsLang.NORTH_SHORT, "N");
        add(TitanRealmsLang.SOUTH_SHORT, "S");
        add(TitanRealmsLang.WEST_SHORT, "W");
        add(TitanRealmsLang.EAST_SHORT, "E");
        // Hold for
        add(TitanRealmsLang.HOLD_FOR_DETAILS, "Hold %s for details.");
        add(TitanRealmsLang.HOLD_FOR_DESCRIPTION, "Hold %s for a description.");
        add(TitanRealmsLang.HOLD_FOR_SUPPORTED_ITEMS, "Hold %s for supporting items.");
        // Side data/config
        add(TitanRealmsLang.SIDE_DATA_NONE, "None");
        // Tooltip Stuff
        add(TitanRealmsLang.INVALID, "(Invalid)");
        add(TitanRealmsLang.HAS_INVENTORY, "Inventory: %s");
        add(TitanRealmsLang.PROGRESS, "Progress: %s");
        add(TitanRealmsLang.PROCESS_RATE, "Process Rate: %s");
        add(TitanRealmsLang.PROCESS_RATE_MB, "Process Rate: %s mB/t");
        // GUI Stuff
        add(TitanRealmsLang.WIDTH, "Width");
        add(TitanRealmsLang.HEIGHT, "Height");
        add(TitanRealmsLang.MIN, "Min: %s");
        add(TitanRealmsLang.MAX, "Max: %s");
        add(TitanRealmsLang.INFINITE, "Infinite");
        add(TitanRealmsLang.NONE, "None");
        add(TitanRealmsLang.EMPTY, "Empty");
        add(TitanRealmsLang.TICKS_REQUIRED, "Ticks Required: %s");
        add(TitanRealmsLang.STORING, "Storing: %s");
        add(TitanRealmsLang.UNIT, "Unit: %s");
        add(TitanRealmsLang.USING, "Using: %s/t");
        add(TitanRealmsLang.NEEDED, "Needed: %s");
        add(TitanRealmsLang.NEEDED_PER_TICK, "Needed: %s/t");
        add(TitanRealmsLang.FINISHED, "Finished: %s");
        add(TitanRealmsLang.NO_RECIPE, "(No recipe)");
        add(TitanRealmsLang.MOVE_UP, "Move Up");
        add(TitanRealmsLang.MOVE_DOWN, "Move Down");
        add(TitanRealmsLang.SET, "Set:");
        add(TitanRealmsLang.TRUE, "True");
        add(TitanRealmsLang.FALSE, "False");
        add(TitanRealmsLang.CLOSE, "Close");
        add(TitanRealmsLang.COLOR_PICKER, "Color Picker");
        add(TitanRealmsLang.RGB, "RGB:");
        add(TitanRealmsLang.OPACITY, "Opacity");
        add(TitanRealmsLang.DEFAULT, "Default");
        add(TitanRealmsLang.WARNING, "Warning");
        add(TitanRealmsLang.DANGER, "Danger");
        // Tab
        add(TitanRealmsLang.MAIN_TAB, "Main");
        // Status
        add(TitanRealmsLang.STATUS, "Status: %s");
        add(TitanRealmsLang.STATUS_OK, "All OK");
        // Boolean Values
        add(TitanRealmsLang.YES, "yes");
        add(TitanRealmsLang.NO, "no");
        add(TitanRealmsLang.ON, "on");
        add(TitanRealmsLang.OFF, "off");
        add(TitanRealmsLang.INPUT, "Input");
        add(TitanRealmsLang.OUTPUT, "Output");
        add(TitanRealmsLang.ACTIVE, "Active");
        add(TitanRealmsLang.DISABLED, "Disabled");
        add(TitanRealmsLang.ON_CAPS, "ON");
        add(TitanRealmsLang.OFF_CAPS, "OFF");
        // Capacity
        add(TitanRealmsLang.CAPACITY, "Capacity: %s");
        add(TitanRealmsLang.CAPACITY_ITEMS, "Capacity: %s Items");
        add(TitanRealmsLang.CAPACITY_MB, "Capacity: %s mB");
        add(TitanRealmsLang.CAPACITY_PER_TICK, "Capacity: %s/t");
        add(TitanRealmsLang.CAPACITY_MB_PER_TICK, "Capacity: %s mB/t");
        // Crafting Formula
        add(TitanRealmsLang.INGREDIENTS, "Ingredients:");
        add(TitanRealmsLang.ENCODED, "(Encoded)");
        // MultiBlock
        add(TitanRealmsLang.MULTIBLOCK_INCOMPLETE, "Incomplete");
        add(TitanRealmsLang.MULTIBLOCK_FORMED, "Formed");
        add(TitanRealmsLang.MULTIBLOCK_CONFLICT, "Conflict");
        add(TitanRealmsLang.MULTIBLOCK_FORMED_CHAT, "Multiblock Formed");
        add(TitanRealmsLang.MULTIBLOCK_INVALID_FRAME, "Couldn't create frame, invalid block at %s.");
        add(TitanRealmsLang.MULTIBLOCK_INVALID_INNER, "Couldn't validate internals, found invalid block at %s.");
        // Button
        add(TitanRealmsLang.BUTTON_CONFIRM, "Confirm");
        add(TitanRealmsLang.BUTTON_START, "Start");
        add(TitanRealmsLang.BUTTON_STOP, "Stop");
        add(TitanRealmsLang.BUTTON_CONFIG, "Config");
        add(TitanRealmsLang.BUTTON_REMOVE, "Remove");
        add(TitanRealmsLang.BUTTON_CANCEL, "Cancel");
        add(TitanRealmsLang.BUTTON_SAVE, "Save");
        add(TitanRealmsLang.BUTTON_SET, "Set");
        add(TitanRealmsLang.BUTTON_DELETE, "Delete");
        add(TitanRealmsLang.BUTTON_OPTIONS, "Options");
        // Descriptions
        add(TitanRealmsLang.DESCRIPTION_TITANBRONZE, "The first Divine alloy created by the Proto-Gods known as the Stone Titans. It takes specific energies and a special forge to produce");
        add(TitanRealmsLang.DESCRIPTION_TITANSTEEL, "The pinnacle of Divine alloys created by the Stone Titans.");
        add(TitanRealmsLang.DESCRIPTION_TITANFORGE, "WIP");

    }

    private void addSubtitles() {

    }

    private void addDamageSources() {

    }

    private void addEntities() {

    }

    private void addBlocks() {
        add(TitanRealmsBlocks.TERRAIN.get(TerrainType.AESIR_STONE), "Aesir Stone");
        add(TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_MARBLE), "Blasted Marble");
        add(TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_STONE), "Blasted Stone");
        add(TitanRealmsBlocks.TERRAIN.get(TerrainType.BLASTED_GLASS), "Blasted Glass");
        add(TitanRealmsBlocks.TERRAIN.get(TerrainType.TITANFORGED_STONE), "Titanforged Stone");
        add(TitanRealmsBlocks.TERRAIN.get(TerrainType.SPARSE_CLOUD_SOIL), "Sparse Cloud Soil");
        add(TitanRealmsBlocks.TERRAIN.get(TerrainType.CLOUD_SOIL), "Cloud Soil");
        add(TitanRealmsBlocks.TERRAIN.get(TerrainType.DENSE_CLOUD_SOIL), "Dense Cloud Soil");

        add(TitanRealmsBlocks.AIRWOOD_LOG, "Airwood Log");
        add(TitanRealmsBlocks.AIRWOOD_PLANKS, "Airwood Planks");
        add(TitanRealmsBlocks.AIRWOOD_STAIRS, "Airwood Stairs");
        add(TitanRealmsBlocks.AIRWOOD_SLAB, "Airwood Slab");
        add(TitanRealmsBlocks.AIRWOOD_FENCE, "Airwood Fence");
        add(TitanRealmsBlocks.AIRWOOD_GATE, "Airwood Gate");
        add(TitanRealmsBlocks.STORMWOOD_LOG, "Stormwood Log");
        add(TitanRealmsBlocks.STORMWOOD_PLANKS, "Stormwood Planks");
    }

    private void addItems() {

    }
}
