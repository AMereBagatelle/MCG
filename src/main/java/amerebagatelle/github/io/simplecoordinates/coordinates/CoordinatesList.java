package amerebagatelle.github.io.simplecoordinates.coordinates;

import java.util.ArrayList;

public class CoordinatesList {
    public ArrayList<CoordinatesSet> coordinatesSets = new ArrayList<>();
    public boolean isNull = false;

    public void addEntry(CoordinatesSet coordinates) {
        this.coordinatesSets.add(coordinates);
    }

    public CoordinatesList createNull() {
        isNull = true;
        return this;
    }
}
