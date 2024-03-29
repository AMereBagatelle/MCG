package amerebagatelle.github.io.mcg.utils;

import amerebagatelle.github.io.mcg.Constants;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;

public class Config {
    public static final File settingsFile = FabricLoader.getInstance().getConfigDir().resolve("mcg.json").toFile();

    public int overlayX, overlayY = 0;
    public String overlayFormat = "%name @ %x %y %z";

    public Config() {
        if (!settingsFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                settingsFile.createNewFile();

                BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
                writer.write("{}");
                writer.flush();
                writer.close();

                writeSetting("overlayX", "20");
                writeSetting("overlayY", "20");
                writeSetting("overlayFormat", "%name @ %x %y %z");
            } catch (IOException e) {
                Constants.LOGGER.error("Couldn't create a settings file.");
                e.printStackTrace();
            }
        }

        loadSettings();
    }

    public void loadSettings() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
            JsonObject settingsJson = Constants.GSON.fromJson(reader, JsonObject.class);
            reader.close();

            try {
                overlayX = settingsJson.get("overlayX").getAsInt();
            } catch (NullPointerException e) {
                writeSetting("overlayX", "20");
            }
            try {
                overlayY = settingsJson.get("overlayY").getAsInt();
            } catch (NullPointerException e) {
                writeSetting("overlayY", "20");
            }
            try {
                overlayFormat = settingsJson.get("overlayFormat").getAsString();
            } catch (NullPointerException e) {
                writeSetting("overlayFormat", "%name @ %x %y %z");
            }
        } catch (IOException e) {
            Constants.LOGGER.error("Couldn't read the settings file.\n");
            e.printStackTrace();
        }
    }

    public void writeSetting(String setting, String value) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
            JsonObject settingsJson = Constants.GSON.fromJson(reader, JsonObject.class);
            reader.close();

            settingsJson.addProperty(setting, value);

            Files.write(settingsFile.toPath(), Constants.GSON.toJson(settingsJson).getBytes());
        } catch (IOException e) {
            Constants.LOGGER.error("Couldn't write to the settings file.\n");
            e.printStackTrace();
        }
    }
}
