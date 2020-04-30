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

import javax.annotation.Nullable;
import java.io.*;
import java.util.*;

public class CoordinatesManager {
    public static final String coordinatesFolder = "coordinates";
    public static File coordinatesFile = null;
    private static final Logger logger = LogManager.getLogger();

    public static void initCoordinates() {
        File coordinatesFolderFile = new File(coordinatesFolder);
        if(!coordinatesFolderFile.exists() || !coordinatesFolderFile.isDirectory()) {
            coordinatesFolderFile.mkdir();
        }
        if(new File("coordinates.json").exists()) {
            logger.warn("Important!!!!  You will not be able to access your coordinates from version <0.1.1 in version 0.2.0.  Please refer to the README on the github page.");
        }
    }
    @Environment(EnvType.CLIENT)
    public static void initCoordinatesForServer(String serverName) {
        MinecraftClient mc = MinecraftClient.getInstance();
        coordinatesFile = new File(coordinatesFolder + "/" + serverName);
        if(!coordinatesFile.exists()) {
            try {
                coordinatesFile.createNewFile();

                BufferedWriter writer = new BufferedWriter(new PrintWriter(coordinatesFile));
                writer.write("{\"default\":{}}");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                logger.error("Could not create coordinates file, can I write?");
            }
            logger.info(I18n.translate("coordinatefile.creation"));
        }
        // ! DATAFIXERS, BEWARE
        try {
            JSONObject coordinateJson = (JSONObject) new JSONParser().parse(new FileReader(coordinatesFile));
            Set<String> coordinateKeys = coordinateJson.keySet();
            if (!coordinateKeys.contains("default")) {
                BufferedWriter writer = new BufferedWriter(new PrintWriter(coordinatesFile));
                writer.write("{\"default\":{}}");
                writer.flush();
                writer.close();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<CoordinateSet> loadCoordinates() throws IOException {
        // Throws IOException if the coordinates file could not be read
     if(coordinatesFile != null && coordinatesFile.exists()) {
         try {
             JSONObject coordinateJson = (JSONObject) new JSONParser().parse(new FileReader(coordinatesFile));
             Set<String> coordinateKeys = coordinateJson.keySet();
             ArrayList<CoordinateSet> coordinateList = new ArrayList<>();
             coordinateKeys.forEach(key -> {
                 Map keyJSON = (Map)coordinateJson.get(key);
                if(keyJSON.containsKey("x")) {
                    CoordinateSet coordinateSet = getCoordinateFromJSON(coordinateJson, key);
                    coordinateSet.setFolder("default");
                    coordinateList.add(coordinateSet);
                } else {
                    Set<String> internalKeySet = keyJSON.keySet();
                    internalKeySet.forEach(internalKey -> {
                        CoordinateSet coordinateSet = getCoordinateFromJSON(keyJSON, internalKey);
                        coordinateSet.setFolder(key);
                        coordinateList.add(coordinateSet);
                    });
                }
             });

             return coordinateList;
         } catch(ParseException e) {
             throw new IOException("Can't load coordinates");
         }
     } else {
        throw new IOException("Can't load coordinates");
     }
    }

    public static CoordinateSet getCoordinateFromJSON(Map JSON, String key) {
        Map coordinate = (Map) JSON.get(key);
        Iterator<Map.Entry> coordinateItr = coordinate.entrySet().iterator();
        CoordinateSet xyzd = new CoordinateSet("Unregistered", 0, 0, 0, "Unregistered");
        xyzd.setName(key);
        Map.Entry pair = coordinateItr.next();
        xyzd.setX(Integer.parseInt(pair.getValue().toString()));
        pair = coordinateItr.next();
        xyzd.setY(Integer.parseInt(pair.getValue().toString()));
        pair = coordinateItr.next();
        xyzd.setZ(Integer.parseInt(pair.getValue().toString()));
        pair = coordinateItr.next();
        xyzd.setDetails(pair.getValue().toString());
        return xyzd;
    }

    public static void writeToCoordinates(CoordinateSet coordinates) throws IOException {
        JSONObject coordinatesJSON = new ObjectMapper().readValue(coordinatesFile, JSONObject.class);
        String coordinateKey = coordinates.getName();

        Map<String, String> m = new LinkedHashMap<>(4);
        m.put("x", Integer.toString(coordinates.getX()));
        m.put("y", Integer.toString(coordinates.getY()));
        m.put("z", Integer.toString(coordinates.getZ()));
        m.put("details", coordinates.getDetails());

        String folder = coordinates.getFolder();

        if(folder.length() == 0) {
            Map<String, Map> insertFolder = new LinkedHashMap<>(1);
            insertFolder.put(coordinateKey, m);
            coordinatesJSON.put("default", insertFolder);
        } else {
            Map<String, Map> insertFolder = new LinkedHashMap<>(1);
            insertFolder.put(coordinateKey, m);
            coordinatesJSON.put(folder, insertFolder);
        }

        BufferedWriter writer = new BufferedWriter(new PrintWriter(coordinatesFile));
        writer.write(coordinatesJSON.toJSONString());
        writer.flush();
        writer.close();
    }

    public static void removeCoordinate(String coordinateKey) throws IOException {
        JSONObject coordinatesJSON = new ObjectMapper().readValue(coordinatesFile, JSONObject.class);
        coordinatesJSON.remove(coordinateKey);
        BufferedWriter writer = new BufferedWriter(new PrintWriter(coordinatesFile));
        writer.write(coordinatesJSON.toJSONString());
        writer.flush();
        writer.close();
    }
}
