package amerebagatelle.github.io.mcg.coordinates;

import net.minecraft.world.dimension.DimensionType;

import java.util.Objects;

public class Coordinate {
    public String name;
    public int x;
    public int y;
    public int z;
    public String description;

    public transient boolean isNether = false;

    public Coordinate(String name, int x, int y, int z, String description) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.description = description;
    }

    public Coordinate(String name, int x, int y, int z, String description, boolean isNether) {
        this(name, x, y, z, description);
        this.isNether = isNether;
    }

    public Coordinate toNetherCoordinateSet() {
        return new Coordinate(name, x / 8, y / 8, z / 8, description, true);
    }

    public Coordinate copy() {
        return new Coordinate(name, x, y, z, description, isNether);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate other = (Coordinate) o;
        return x == other.x && y == other.y && z == other.z && Objects.equals(name, other.name) && Objects.equals(description, other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, z, description);
    }
}
