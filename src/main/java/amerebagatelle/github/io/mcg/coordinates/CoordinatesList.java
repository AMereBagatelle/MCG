package amerebagatelle.github.io.mcg.coordinates;

import java.util.ArrayList;

public class CoordinatesList {
    public ArrayList<CoordinatesSet> coordinatesSet = new ArrayList<>();
    public boolean isNull = false;

    public void addEntry(CoordinatesSet coordinates) {
        this.coordinatesSet.add(coordinates);
    }

    public CoordinatesList createNull() {
        isNull = true;
        return this;
    }
}
