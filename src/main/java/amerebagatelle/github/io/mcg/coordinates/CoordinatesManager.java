package amerebagatelle.github.io.mcg.coordinates;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CoordinatesManager {
    public final Path coordinatesFolder = getCoordinateDirectory();
    private final Logger logger = LogManager.getLogger();
    private final Gson gson;

    public CoordinatesManager() {
        gson = new Gson();
    }

    public void initCoordinatesFolder() throws IOException {
        File coordinatesFolderFile = new File(coordinatesFolder.toUri());
        if (!coordinatesFolderFile.exists() && !coordinatesFolderFile.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            coordinatesFolderFile.mkdir();
        }
        if (coordinatesFolderFile.isDirectory() && Objects.requireNonNull(coordinatesFolderFile.listFiles()).length == 0) {
            initNewCoordinatesFile(Paths.get(coordinatesFolder.toString(), "newCoordinates.coordinates"));
        }
    }

    public void initNewCoordinatesFile(Path filepath) throws IOException {
        File coordinatesFile = new File(filepath.toUri());
        Path coordinatesFilePath = coordinatesFile.toPath();
        if (coordinatesFile.exists()) return;

        try {
            if (coordinatesFile.createNewFile()) {
                Files.write(coordinatesFilePath, "{}".getBytes());
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            logger.info("Could not create coordinates file.");
            throw new IOException("Couldn't create coordinates file.");
        }
    }

    public void createFolder(Path filepath) {
        File folderFile = new File(filepath.toUri());
        if (folderFile.exists()) return;
        //noinspection ResultOfMethodCallIgnored
        folderFile.mkdir();
    }

    public List<CoordinatesSet> loadCoordinates(Path filepath) throws IOException {
        File coordinatesFile = new File(filepath.toUri());
        if (!coordinatesFile.exists()) return null;

        List<CoordinatesSet> loadedList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(coordinatesFile));
        JsonObject coordinatesJson = gson.fromJson(reader, JsonObject.class);
        reader.close();

        for (Map.Entry<String, JsonElement> entry : coordinatesJson.entrySet()) {
            CoordinatesSet coordinatesParsed = gson.fromJson(entry.getValue(), CoordinatesSet.class);
            coordinatesParsed.name = entry.getKey();
            loadedList.add(coordinatesParsed);
        }

        return loadedList;
    }

    public void writeToCoordinates(Path filepath, CoordinatesSet coordinates) throws IOException {
        String jsonAsString = new String(Files.readAllBytes(filepath));
        JsonObject coordinatesJson = gson.fromJson(jsonAsString, JsonObject.class);

        Map<String, String> map = new LinkedHashMap<>();
        map.put("x", Integer.toString(coordinates.x));
        map.put("y", Integer.toString(coordinates.y));
        map.put("z", Integer.toString(coordinates.z));
        map.put("description", coordinates.description);
        JsonElement mapToElement = gson.toJsonTree(map);

        coordinatesJson.add(coordinates.name, mapToElement);

        Files.write(filepath, gson.toJson(coordinatesJson).getBytes());
    }

    public void removeCoordinate(Path filepath, CoordinatesSet coordinate) throws IOException {
        String jsonAsString = new String(Files.readAllBytes(filepath));
        JsonObject coordinatesJson = gson.fromJson(jsonAsString, JsonObject.class);

        coordinatesJson.remove(coordinate.name);

        Files.write(filepath, gson.toJson(coordinatesJson).getBytes());
    }

    public static Path getCoordinateDirectory() {
        return Paths.get(FabricLoader.getInstance().getGameDir().toString(), "coordinates");
    }
}
