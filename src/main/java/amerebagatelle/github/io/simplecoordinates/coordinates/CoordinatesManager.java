package amerebagatelle.github.io.simplecoordinates.coordinates;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class CoordinatesManager {
    private static final File coordinatesFile = new File("coordinates.json");
    private static final Logger logger = LogManager.getLogger();

    public static void initCoordinates() {
        if(!coordinatesFile.exists()) {
            try {
                coordinatesFile.createNewFile();
            } catch (IOException e) {
                logger.error("Could not create coordinates file, can I write?");
            }
        }
    }

    public static ArrayList<ArrayList<String>> loadCoordinates() throws IOException {
        // Throws IOException if the coordinates file could not be read
     if(coordinatesFile.exists()) {
         try {
             JSONObject coordinateJson = (JSONObject) new JSONParser().parse(new FileReader(coordinatesFile));
             Set<String> coordinateKeys = coordinateJson.keySet();
             ArrayList<ArrayList<String>> coordinateList = new ArrayList<>();
             coordinateKeys.forEach(key -> {
                 Map coordinate = (Map)coordinateJson.get(key);
                 Iterator<Map.Entry> coordinateItr = coordinate.entrySet().iterator();
                 ArrayList<String> xyz = new ArrayList<>();
                 xyz.add(key);
                 while(coordinateItr.hasNext()) {
                     Map.Entry pair = coordinateItr.next();
                     xyz.add(pair.getValue().toString());
                 }
                 coordinateList.add(xyz);
             });

             return coordinateList;
         } catch(ParseException e) {
             throw new IOException("Can't load coordinates");
         }
     } else {
        throw new IOException("Can't load coordinates");
     }
    }

    public static void writeToCoordinates(String coordinateKey, int x, int y, int z) throws IOException {
        JSONObject coordinatesJSON = new ObjectMapper().readValue(coordinatesFile, JSONObject.class);

        Map<String, Integer> m = new LinkedHashMap<String, Integer>(3);
        m.put("x", x);
        m.put("y", y);
        m.put("z", z);

        coordinatesJSON.put(coordinateKey, m);

        BufferedWriter writer = new BufferedWriter(new PrintWriter(coordinatesFile));
        writer.write(coordinatesJSON.toJSONString());
        writer.flush();
        writer.close();
    }
}
