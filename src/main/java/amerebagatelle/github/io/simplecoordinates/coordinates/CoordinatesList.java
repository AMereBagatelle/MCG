package amerebagatelle.github.io.simplecoordinates.coordinates;

import java.util.ArrayList;

public class CoordinatesList {
    public ArrayList<CoordinatesSet> coordinatesSets = new ArrayList<>();

    public void addEntry(CoordinatesSet coordinates) {
        this.coordinatesSets.add(coordinates);
    }
}
