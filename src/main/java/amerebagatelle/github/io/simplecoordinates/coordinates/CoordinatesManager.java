package amerebagatelle.github.io.simplecoordinates.coordinates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class CoordinatesManager {
    public final String coordinatesFolder = "coordinates";
    private final Logger logger = LogManager.getLogger();
    private final Gson gson;
    
    public CoordinatesManager() {
        gson = new Gson();
    }

    public void initCoordinatesFolder() {
        File coordinatesFolderFile = new File(coordinatesFolder);
        if(!coordinatesFolderFile.exists() && !coordinatesFolderFile.isDirectory()) {
            coordinatesFolderFile.mkdir();
        }
    }

    public CoordinatesList loadCoordinates(String filepath) throws IOException {
        File coordinatesFile = new File(filepath);
        if(!coordinatesFile.exists()) return new CoordinatesList().createNull();

        CoordinatesList loadedList = new CoordinatesList();
        BufferedReader reader = new BufferedReader(new FileReader(coordinatesFile));
        JsonObject coordinatesJson = gson.fromJson(reader, JsonObject.class);
        reader.close();

        for (Map.Entry<String, JsonElement> entry : coordinatesJson.entrySet()) {
            CoordinatesSet coordinatesParsed = gson.fromJson(entry.getValue(), CoordinatesSet.class);
            loadedList.addEntry(coordinatesParsed);
        }
        
        return loadedList;
    }

    public void writeToCoordinates(String filepath, CoordinatesSet coordinates) throws IOException {
        File coordinatesFile = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(coordinatesFile));
        JsonObject coordinatesJson = gson.fromJson(reader, JsonObject.class);
        reader.close();

        coordinatesJson.add(coordinates.name, gson.toJsonTree(coordinates));

        BufferedWriter writer = new BufferedWriter(new FileWriter(coordinatesFile));
        writer.write(gson.toJson(coordinatesJson));
    }

    public void removeCoordinate(String filepath, CoordinatesSet coordinates) throws IOException {
        File coordinatesFile = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(coordinatesFile));
        JsonObject coordinatesJson = gson.fromJson(reader, JsonObject.class);
        reader.close();

        coordinatesJson.remove(coordinates.name);

        BufferedWriter writer = new BufferedWriter(new FileWriter(coordinatesFile));
        writer.write(gson.toJson(coordinatesJson));
    }
}
