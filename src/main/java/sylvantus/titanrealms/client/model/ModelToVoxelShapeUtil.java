package sylvantus.titanrealms.client.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sylvantus.titanrealms.TitanRealms;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.stream.Stream;

public class ModelToVoxelShapeUtil {

    public static void main(String[] args) {
        printoutModelFile("/Users/aidancbrady/Documents/Mekanism/src/main/resources/assets/mekanism/models/block/digital_miner.json");
    }

    public static void printoutModelFile(String path) {
        StringBuilder builder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject obj = new JsonParser().parse(builder.toString()).getAsJsonObject();
        if (obj.has("elements")) {
            printoutObject(obj);
        } else if (obj.has("layers")) {
            obj.get("layers").getAsJsonObject().entrySet().forEach(e -> printoutObject(e.getValue().getAsJsonObject()));
        } else {
            TitanRealms.LOGGER.error("Unable to parse model file.");
        }
    }

    private static void printoutObject(JsonObject obj) {
        JsonArray array = obj.get("elements").getAsJsonArray();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.####", otherSymbols);
        for (int i = 0; i < array.size(); i++) {
            JsonObject element = array.get(i).getAsJsonObject();
            JsonArray from = element.get("from").getAsJsonArray();
            JsonArray to = element.get("to").getAsJsonArray();
            JsonElement nameObj = element.get("name");
            String name = nameObj != null ? " // " + nameObj.getAsString() : "";
            String fromText = df.format(from.get(0).getAsDouble()) + ", " + df.format(from.get(1).getAsDouble()) + ", " + df.format(from.get(2).getAsDouble());
            String toText = df.format(to.get(0).getAsDouble()) + ", " + df.format(to.get(1).getAsDouble()) + ", " + df.format(to.get(2).getAsDouble());
            System.out.println("makeCuboidShape(" + fromText + ", " + toText + ")" + (i < array.size() - 1 ? "," : "") + name);
        }
    }
}
