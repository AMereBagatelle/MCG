package amerebagatelle.github.io.mcg.coordinates;

import amerebagatelle.github.io.mcg.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CoordinateFile {
    private final Path filePath;
    private final List<Coordinate> coordinates;

    public CoordinateFile(Path filePath) throws IOException {
        if (!Files.exists(filePath)) Files.write(Files.createFile(filePath), "[]".getBytes());
        this.filePath = filePath;

        coordinates = loadCoordinates();
    }

    private List<Coordinate> loadOldCoordinates() throws IOException {
        List<Coordinate> loadedList = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(filePath);
        JsonObject coordinatesJson = Constants.GSON.fromJson(reader, JsonObject.class);
        reader.close();

        for (Map.Entry<String, JsonElement> entry : coordinatesJson.entrySet()) {
            Coordinate coordinatesParsed = Constants.GSON.fromJson(entry.getValue(), Coordinate.class);
            coordinatesParsed.name = entry.getKey();
            loadedList.add(coordinatesParsed);
        }

        return loadedList;
    }

    private List<Coordinate> loadCoordinates() throws IOException {
        List<Coordinate> loadedList = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(filePath);
        JsonArray coordinatesJson;
        try {
            coordinatesJson = Constants.GSON.fromJson(reader, JsonArray.class);
        } catch (JsonSyntaxException e) {
            return loadOldCoordinates();
        }

        reader.close();

        for (JsonElement entry : coordinatesJson.asList()) {
            Coordinate coordinatesParsed = Constants.GSON.fromJson(entry, Coordinate.class);
            loadedList.add(coordinatesParsed);
        }

        return loadedList;
    }

    public void addCoordinate(Coordinate coordinate) throws IllegalArgumentException {
        // Update instead of just adding if a coordinate has the same name
        if (!coordinates.stream().filter(coord -> Objects.equals(coord.name, coordinate.name)).toList().isEmpty()) {
            this.removeCoordinate(coordinate);
        }

        coordinates.add(coordinate);
    }

    /**
     * Removes a single coordinate by values.
     *
     * @param coordinate Coordinates to remove.
     * @return Whether the coordinate was removed.
     */
    public boolean removeCoordinate(Coordinate coordinate) {
        return coordinates.removeIf(coord -> coord.equals(coordinate));
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void save() throws IOException {
        Files.write(filePath, Constants.GSON.toJson(coordinates).getBytes());
    }

    /**
     * This is only valid once on a given object.  After its usage, the object becomes INVALID and cannot be used.
     *
     * @throws IOException If deleting the file failed.
     */
    public void delete() throws IOException {
        Files.deleteIfExists(filePath);
    }

    public String getName() {
        return filePath.getFileName().toString();
    }

}
