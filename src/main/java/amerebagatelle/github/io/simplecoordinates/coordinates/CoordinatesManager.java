package amerebagatelle.github.io.simplecoordinates.coordinates;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public static final String coordinatesFolder = "coordinates";
    private static final Logger logger = LogManager.getLogger();

    public void initCoordinatesFolder() {
        File coordinatesFolderFile = new File(coordinatesFolder);
        if(!coordinatesFolderFile.exists() && !coordinatesFolderFile.isDirectory()) {
            coordinatesFolderFile.mkdir();
        }
    }

    public ArrayList<ArrayList<String>> loadCoordinates() throws IOException {
        return null;
    }

    public void writeToCoordinates(String coordinateKey, int x, int y, int z, String details) throws IOException {
    }

    public void removeCoordinate(String coordinateKey) throws IOException {
    }
}
