package amerebagatelle.github.io.simplecoordinates.coordinates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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
import java.nio.file.Files;
import java.nio.file.Path;
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

    public void initNewCoordinatesFile(String filepath) throws IOException {
        File coordinatesFile = new File(filepath);
        Path coordinatesFilePath = coordinatesFile.toPath();
        if(coordinatesFile.exists()) return;

        try {
            if(coordinatesFile.createNewFile()) {
                Files.write(coordinatesFilePath, "{}".getBytes());
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            logger.info("Could not create coordinates file.");
            throw new IOException("Couldn't create coordinates file.");
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
            coordinatesParsed.name = entry.getKey();
            loadedList.addEntry(coordinatesParsed);
        }
        
        return loadedList;
    }

    public void writeToCoordinates(String filepath, CoordinatesSet coordinates) throws IOException {
        Path coordinatesFilepath = new File(filepath).toPath();
        String jsonAsString = new String(Files.readAllBytes(coordinatesFilepath));
        JsonObject coordinatesJson = gson.fromJson(jsonAsString, JsonObject.class);

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("x", Integer.toString(coordinates.x));
        map.put("y", Integer.toString(coordinates.y));
        map.put("z", Integer.toString(coordinates.z));
        map.put("description", coordinates.description);
        JsonElement mapToElement = gson.toJsonTree(map);

        coordinatesJson.add(coordinates.name, mapToElement);

        Files.write(coordinatesFilepath, gson.toJson(coordinatesJson).getBytes());
    }

    public void removeCoordinate(String filepath, CoordinatesSet coordinates) throws IOException {
        Path coordinatesFilepath = new File(filepath).toPath();
        String jsonAsString = new String(Files.readAllBytes(coordinatesFilepath));
        JsonObject coordinatesJson = (JsonObject)gson.toJsonTree(jsonAsString);

        coordinatesJson.remove(coordinates.name);

        Files.write(coordinatesFilepath, gson.toJson(coordinatesJson).getBytes());
    }
}
