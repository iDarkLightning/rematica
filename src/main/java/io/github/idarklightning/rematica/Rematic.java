package io.github.idarklightning.rematica;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;

public class Rematic {
    private static final Type TYPE = new TypeToken<ArrayList<Rematic>>(){}.getType();
    private static final Gson SERIALIZER = new GsonBuilder().setPrettyPrinting().create();

    private final String name;
    private final String searchURL;

    public Rematic(String name, String searchURL) {
        this.name = name;
        this.searchURL = searchURL;
    }

    public String getName() {
        return name;
    }

    public String getSearchURL() {
        return searchURL;
    }

    public static ArrayList<Rematic> loadFromFile(String fileName) {
        File configDir = FabricLoader.getInstance().getConfigDir().toFile();
        File rematicsFile = new File(configDir, fileName);

        if (!rematicsFile.exists()) {
            try (FileWriter writer = new FileWriter(rematicsFile)) {
                writer.write(SERIALIZER.toJson(new ArrayList<>()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Optional<ArrayList<Rematic>> rematics;

        try (FileReader reader = new FileReader(rematicsFile)) {
            rematics = Optional.of(SERIALIZER.fromJson(reader, TYPE));
        } catch (IOException exception) {
            rematics = Optional.empty();
        }

        if (!rematics.isPresent()) {
            System.out.println("Error go brr, no config file");
            return null;
        } else {
            return rematics.get();
        }
    }

    public static void saveToFile(String fileName) {
        File configDir = FabricLoader.getInstance().getConfigDir().toFile();
        File rematicsFile = new File(configDir, fileName);

        try (FileWriter writer = new FileWriter(rematicsFile)) {
            writer.write(SERIALIZER.toJson(Rematica.REMATICS));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
