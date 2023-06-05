package amerebagatelle.github.io.mcg;

import amerebagatelle.github.io.mcg.coordinates.CoordinateRoot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CoordinateLoadingTest {
    static CoordinateRoot root;

    @BeforeAll
    static void setup() throws IOException {
        root = new CoordinateRoot(Path.of("src/test/resources/"));
    }

    @Test()
    void loadNewCoordinateSet() {
        var file = assertDoesNotThrow(() -> root.getFile("testCoordinates.json").orElseThrow());

        assertFalse(file.getCoordinates().isEmpty());
    }

    @Test()
    void loadOldCoordinateSet() {
        var file = assertDoesNotThrow(() -> root.getFile("oldStyleCoordinates.coordinates").orElseThrow());

        assertFalse(file.getCoordinates().isEmpty());
    }
}
