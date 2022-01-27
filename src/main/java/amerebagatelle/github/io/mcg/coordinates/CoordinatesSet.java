package amerebagatelle.github.io.mcg.coordinates;

public class CoordinatesSet {
    public String name;
    public int x;
    public int y;
    public int z;
    public String description;

    public CoordinatesSet(String name, int x, int y, int z, String description) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.description = description;
    }

    public CoordinatesSet toNetherCoordinateSet() {
        return new CoordinatesSet(name, x / 8, y / 8, z / 8, description);
    }
}
