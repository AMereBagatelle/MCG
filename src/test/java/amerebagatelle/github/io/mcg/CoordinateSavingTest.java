package amerebagatelle.github.io.mcg;

import amerebagatelle.github.io.mcg.coordinates.Coordinate;
import amerebagatelle.github.io.mcg.coordinates.CoordinateRoot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoordinateSavingTest {
    CoordinateRoot root;

    @BeforeEach
    void resetCoordinateFolder() throws IOException {
        Path rootPath = Path.of("./test/coordinates");
        Files
                .walk(rootPath)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        root = new CoordinateRoot(rootPath);
    }

    @Test()
    void coordinateSaving() {
        var file = assertDoesNotThrow(() -> root.getFile("test.json").orElseThrow());

        Coordinate testSet = new Coordinate("test1", 1, 1, 1, "test2");
        file.addCoordinate(testSet);
        assertDoesNotThrow(file::save);
    }

    @Test()
    void coordinateRemoval() {
        var file = assertDoesNotThrow(() -> root.getFile("test.json").orElseThrow());
        Coordinate testSet = new Coordinate("test1", 1, 1, 1, "test2");
        file.addCoordinate(testSet);

        testSet = new Coordinate("test1", 1, 1, 1, "test2");
        assertTrue(file.removeCoordinate(testSet));
    }

    @Test()
    void coordinateFileDeletion() {
        var file = assertDoesNotThrow(() -> root.getFile("test.json").orElseThrow());

        Coordinate testSet = new Coordinate("test1", 1, 1, 1, "test2");
        file.addCoordinate(testSet);
        assertDoesNotThrow(file::save);

        assertDoesNotThrow(file::delete);
    }
}
