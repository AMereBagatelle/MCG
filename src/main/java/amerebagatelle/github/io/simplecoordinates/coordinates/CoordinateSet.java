package amerebagatelle.github.io.simplecoordinates.coordinates;

public class CoordinateSet {
    public String name;
    public int x;
    public int y;
    public int z;
    public String details;
    public String folder;

    public CoordinateSet(String name, int x, int y, int z, String details) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
