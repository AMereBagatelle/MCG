package amerebagatelle.github.io.simplecoordinates.coordinates;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    }

    public static void initCoordinatesForServer(String serverName) {
        MinecraftClient mc = MinecraftClient.getInstance();
        coordinatesFile = new File(coordinatesFolder + "/" + serverName);
        if(!coordinatesFile.exists()) {
            try {
                coordinatesFile.createNewFile();

                BufferedWriter writer = new BufferedWriter(new PrintWriter(coordinatesFile));
                writer.write("{}");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                logger.error("Could not create coordinates file, can I write?");
            }
        }
    }

    public static ArrayList<ArrayList<String>> loadCoordinates() throws IOException {
        // Throws IOException if the coordinates file could not be read
     if(coordinatesFile != null && coordinatesFile.exists()) {
         try {
             JSONObject coordinateJson = (JSONObject) new JSONParser().parse(new FileReader(coordinatesFile));
             Set<String> coordinateKeys = coordinateJson.keySet();
             ArrayList<ArrayList<String>> coordinateList = new ArrayList<>();
             coordinateKeys.forEach(key -> {
                 Map coordinate = (Map)coordinateJson.get(key);
                 Iterator<Map.Entry> coordinateItr = coordinate.entrySet().iterator();
                 ArrayList<String> xyzd = new ArrayList<>();
                 xyzd.add(key);
                 while(coordinateItr.hasNext()) {
                     Map.Entry pair = coordinateItr.next();
                     xyzd.add(pair.getValue().toString());
                 }
                 coordinateList.add(xyzd);
             });

             return coordinateList;
         } catch(ParseException e) {
             throw new IOException("Can't load coordinates");
         }
     } else {
        throw new IOException("Can't load coordinates");
     }
    }

    public static void writeToCoordinates(String coordinateKey, int x, int y, int z, String details) throws IOException {
        JSONObject coordinatesJSON = new ObjectMapper().readValue(coordinatesFile, JSONObject.class);

        Map<String, String> m = new LinkedHashMap<>(4);
        m.put("x", Integer.toString(x));
        m.put("y", Integer.toString(y));
        m.put("z", Integer.toString(z));
        m.put("details", details);


        coordinatesJSON.put(coordinateKey, m);

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
